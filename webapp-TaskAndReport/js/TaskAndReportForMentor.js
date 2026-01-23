const api = {
    createTask: "http://localhost:8081/api/task",
    getMyTask: "http://localhost:8081/api/task/",
    getMyTaskForMentor: "http://localhost:8081/api/task/"
}

const tokenTestForMentor = "";

const dataTest = {
    projects: [
        { id: 'proj-001', title: 'AI Chatbot Development', status: 'IN_PROGRESS' },
        { id: 'proj-002', title: 'E-commerce Platform', status: 'IN_PROGRESS' }
    ],
    talents: [
        { id: 'talent-001', name: 'Nguyễn Văn A', email: 'vana@example.com', role: 'TALENT' },
        { id: 'talent-002', name: 'Trần Thị B', email: 'thib@example.com', role: 'TALENT' },
        { id: 'talent-003', name: 'Lê Văn C', email: 'vanc@example.com', role: 'TALENT' }
    ],
    mentor: "mentor-001",
    tasks: [],
    reports: [
        {
            id: 'report-001',
            projectId: 'proj-001',
            reportType: 'MONTHLY',
            title: 'Báo cáo tháng 1/2026',
            summary: 'Hoàn thành 80% công việc theo kế hoạch',
            fileUrl: null,
            status: 'SUBMITTED',
            submittedAt: '2026-01-22T10:30:00'
        }
    ],
    selectedProject: 'proj-001',
    activeTab: 'tasks',
    editingTask: null,
    selectedTalents: []
};

function getStatusBadge(status) {
    const styles = {
        TODO: 'bg-gray-100 text-gray-800',
        IN_PROGRESS: 'bg-blue-100 text-blue-800',
        COMPLETED: 'bg-green-100 text-green-800',
        SUBMITTED: 'bg-yellow-100 text-yellow-800',
        APPROVED: 'bg-green-100 text-green-800',
        REJECTED: 'bg-red-100 text-red-800'
    };

    const labels = {
        TODO: 'Chưa bắt đầu',
        IN_PROGRESS: 'Đang thực hiện',
        COMPLETED: 'Hoàn thành',
        SUBMITTED: 'Đã nộp',
        APPROVED: 'Đã duyệt',
        REJECTED: 'Bị từ chối'
    };

    return `<span class="px-3 py-1 rounded-full text-xs font-medium ${styles[status]}">${labels[status]}</span>`;
}

function getPriorityBadge(priority) {
    const styles = {
        LOW: 'bg-gray-100 text-gray-600',
        MEDIUM: 'bg-yellow-100 text-yellow-700',
        HIGH: 'bg-red-100 text-red-700'
    };

    const labels = {
        LOW: 'Thấp',
        MEDIUM: 'Trung bình',
        HIGH: 'Cao'
    };

    return `<span class="px-2 py-1 rounded text-xs font-medium ${styles[priority]}">${labels[priority]}</span>`;
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('vi-VN');
}

function formatDateTime(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString('vi-VN');
}

function renderProjectSelector() {
    const selector = document.getElementById('projectSelector');
    selector.innerHTML = dataTest.projects.map(p =>
        `<option value="${p.id}" ${p.id === dataTest.selectedProject ? 'selected' : ''}>${p.title}</option>`
    ).join('');
}

function renderTasks() {
    const tasksList = document.getElementById('tasksList');

    fetch(api.getMyTaskForMentor + dataTest.selectedProject)
        .then(response => response.json())
        .then(data => {
            tasks = data.result;

            console.log(tasks);

            const filteredTasks = tasks;

            if (filteredTasks.length === 0) {
                tasksList.innerHTML = `
            <div class="text-center py-12 bg-white rounded-lg shadow">
                <svg class="mx-auto w-12 h-12 text-gray-400 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"></path>
                </svg>
                <p class="text-gray-500">Chưa có task nào. Tạo task mới để bắt đầu!</p>
            </div>
        `;
                return;
            }

            tasksList.innerHTML = filteredTasks.map(task => {
                const assignedTalents = task.talentIds.map(talentId => {
                    const talent = dataTest.talents.find(t => t.id === talentId);
                    return `
                <div class="w-8 h-8 rounded-full bg-blue-500 text-white flex items-center justify-center text-xs font-medium border-2 border-white" title="${talent?.name}">
                    ${talent?.name.charAt(0)}
                </div>
            `;
                }).join('');

                return `
            <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 hover:shadow-md transition ">
                <div class="flex items-start justify-between mb-4">
                    <div class="flex-1">
                        <div class="flex items-center space-x-3 mb-2">
                            <h3 class="text-lg font-semibold text-gray-900">${task.taskName}</h3>
                            ${getStatusBadge(task.status)}
                            ${getPriorityBadge(task.priority)}
                        </div>
                        <p class="text-gray-600 text-sm">${task.description}</p>
                    </div>
                    
                    <div class="flex items-center space-x-2 ml-4">
                        <button onclick="editTask('${task.id}')" class="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
                            </svg>
                        </button>
                        <button onclick="deleteTask('${task.id}')" class="p-2 text-red-600 hover:bg-red-50 rounded-lg transition">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
                            </svg>
                        </button>
                    </div>
                </div>
                
                <div class="flex items-center justify-between text-sm">
                    <div class="flex items-center space-x-4 text-gray-500">
                        <div class="flex items-center space-x-1">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
                            </svg>
                            <span>Hạn: ${formatDate(task.dueDate)}</span>
                        </div>
                        <div class="flex items-center space-x-1">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"></path>
                            </svg>
                            <span>${task.talentIds.length} người</span>
                        </div>
                    </div>
                    
                    <div class="flex -space-x-2">
                        ${assignedTalents}
                    </div>
                </div>
            </div>
        `;
            }).join('');
        });


}

function renderReports() {
    const reportsList = document.getElementById('reportsList');
    const filteredReports = dataTest.reports.filter(r => r.projectId === dataTest.selectedProject);

    if (filteredReports.length === 0) {
        reportsList.innerHTML = `
            <div class="text-center py-12 bg-white rounded-lg shadow">
                <svg class="mx-auto w-12 h-12 text-gray-400 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
                </svg>
                <p class="text-gray-500">Chưa có báo cáo nào. Gửi báo cáo đầu tiên!</p>
            </div>
        `;
        return;
    }

    reportsList.innerHTML = filteredReports.map(report => {
        const reportTypeLabels = {
            MONTHLY: 'Hàng tháng',
            PHASE: 'Theo giai đoạn',
            FINAL: 'Cuối cùng'
        };

        return `
            <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <div class="flex items-start justify-between mb-4">
                    <div class="flex-1">
                        <div class="flex items-center space-x-3 mb-2">
                            <h3 class="text-lg font-semibold text-gray-900">${report.title}</h3>
                            ${getStatusBadge(report.status)}
                            <span class="px-3 py-1 bg-purple-100 text-purple-800 rounded-full text-xs font-medium">
                                ${reportTypeLabels[report.reportType]}
                            </span>
                        </div>
                        <p class="text-gray-600 text-sm mb-3">${report.summary}</p>
                        
                        ${report.fileUrl ? `
                            <div class="flex items-center space-x-2 text-sm text-blue-600">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
                                </svg>
                                <span>Đã đính kèm file báo cáo</span>
                            </div>
                        ` : ''}
                    </div>
                </div>
                
                <div class="flex items-center text-sm text-gray-500">
                    <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                    </svg>
                    <span>Gửi lúc: ${formatDateTime(report.submittedAt)}</span>
                </div>
            </div>
        `;
    }).join('');
}

function renderTalentsList() {
    const talentsList = document.getElementById('talentsList');

    talentsList.innerHTML = dataTest.talents.map(talent => `
        <label class="flex items-center p-3 border border-gray-200 rounded-lg hover:bg-gray-50 cursor-pointer">
            <input type="checkbox" 
                   value="${talent.id}" 
                   ${dataTest.selectedTalents.includes(talent.id) ? 'checked' : ''}
                   onchange="toggleTalent('${talent.id}')"
                   class="w-4 h-4 text-blue-600 rounded focus:ring-blue-500">
            <div class="ml-3 flex-1">
                <div class="flex items-center justify-between">
                    <span class="font-medium text-gray-900">${talent.name}</span>
                    <span class="text-xs text-gray-500">${talent.role}</span>
                </div>
                <p class="text-sm text-gray-500">${talent.email}</p>
            </div>
        </label>
    `).join('');
}
function openTaskModal(task = null) {
    const modal = document.getElementById('taskModal');
    const modalTitle = document.getElementById('taskModalTitle');
    const submitText = document.getElementById('taskSubmitText');

    if (task) {
        dataTest.editingTask = task;
        dataTest.selectedTalents = [...task.assignedTo];

        modalTitle.textContent = 'Chỉnh sửa Task';
        submitText.textContent = 'Cập nhật';

        document.getElementById('taskTitle').value = task.title;
        document.getElementById('taskDescription').value = task.description;
        document.getElementById('taskDueDate').value = task.dueDate;
        document.getElementById('taskPriority').value = task.priority;
        document.getElementById('taskStatus').value = task.status;
    } else {
        dataTest.editingTask = null;
        dataTest.selectedTalents = [];

        modalTitle.textContent = 'Tạo Task Mới';
        submitText.textContent = 'Tạo Task';

        document.getElementById('taskForm').reset();
    }

    renderTalentsList();
    modal.classList.remove('hidden');
}

function closeTaskModal() {
    document.getElementById('taskModal').classList.add('hidden');
    dataTest.editingTask = null;
    dataTest.selectedTalents = [];
}

function toggleTalent(talentId) {
    if (dataTest.selectedTalents.includes(talentId)) {
        dataTest.selectedTalents = dataTest.selectedTalents.filter(id => id !== talentId);
    } else {
        dataTest.selectedTalents.push(talentId);
    }
}

function handleTaskSubmit(e) {
    e.preventDefault();

    //const token = localStorage.getItem('token');

    if (dataTest.selectedTalents.length === 0) {
        alert('Vui lòng chọn ít nhất 1 talent!');
        return;
    }



    console.log("Selected talents:", dataTest.selectedTalents);
    const taskData = {
        "projectId": dataTest.selectedProject,
        "talentIds": dataTest.selectedTalents,
        "mentorId": dataTest.mentor,
        "taskName": document.getElementById('taskTitle').value,
        "description": document.getElementById('taskDescription').value,
        "priority": document.getElementById('taskPriority').value,
        "status": document.getElementById('taskStatus').value,
        "dueDate": document.getElementById('taskDueDate').value? new Date(document.getElementById('taskDueDate').value).toISOString()
        : null,
    };

    if (dataTest.editingTask) {
        const index = dataTest.tasks.findIndex(t => t.id === dataTest.editingTask.id);
        dataTest.tasks[index] = {
            ...dataTest.tasks[index],
            ...taskData
        };
    } else {
        fetch(api.createTask, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                //"Authorization": `Bearer ${tokenTestForMentor}`
            },
            body: JSON.stringify(taskData)
        }).then(response => response.json())
            .then(data => {
                alert("Tạo task thành công!");
            });
    }
    closeTaskModal();
    renderTasks();
}

function editTask(taskId) {
    alert("Chức năng chỉnh sửa đang được phát triển!");
    const task = dataTest.tasks.find(t => t.id === taskId);
    if (task) {
        openTaskModal(task);
    }
}

function deleteTask(taskId) {
    alert("Chức năng xoá đang được phát triển!");
}

function openReportModal() {
    const modal = document.getElementById('reportModal');
    document.getElementById('reportForm').reset();
    document.getElementById('fileSelected').classList.add('hidden');
    modal.classList.remove('hidden');
}

function closeReportModal() {
    document.getElementById('reportModal').classList.add('hidden');
}

function handleReportSubmit(e) {
    e.preventDefault();

    const fileInput = document.getElementById('reportFile');

    const newReport = {
        projectId: dataTest.selectedProject,
        reportType: document.getElementById('reportType').value,
        title: document.getElementById('reportTitle').value,
        summary: document.getElementById('reportSummary').value,
        fileUrl: fileInput.files.length > 0 ? URL.createObjectURL(fileInput.files[0]) : null,
        status: 'SUBMITTED',
        submittedAt: new Date().toISOString()
    };

    dataTest.reports.push(newReport);
    closeReportModal();
    renderReports();
}

function handleFileUpload(e) {
    const file = e.target.files[0];
    const fileSelected = document.getElementById('fileSelected');

    if (file) {
        fileSelected.classList.remove('hidden');
    } else {
        fileSelected.classList.add('hidden');
    }
}

function switchTab(tab) {
    dataTest.activeTab = tab;

    const tasksTab = document.getElementById('tasksTab');
    const reportsTab = document.getElementById('reportsTab');
    const tasksContent = document.getElementById('tasksContent');
    const reportsContent = document.getElementById('reportsContent');

    if (tab === 'tasks') {
        tasksTab.classList.add('border-blue-500', 'text-blue-600');
        tasksTab.classList.remove('border-transparent', 'text-gray-500');
        reportsTab.classList.remove('border-blue-500', 'text-blue-600');
        reportsTab.classList.add('border-transparent', 'text-gray-500');

        tasksContent.classList.remove('hidden');
        reportsContent.classList.add('hidden');
    } else {
        reportsTab.classList.add('border-blue-500', 'text-blue-600');
        reportsTab.classList.remove('border-transparent', 'text-gray-500');
        tasksTab.classList.remove('border-blue-500', 'text-blue-600');
        tasksTab.classList.add('border-transparent', 'text-gray-500');

        reportsContent.classList.remove('hidden');
        tasksContent.classList.add('hidden');
    }
}

function handleProjectChange(e) {
    dataTest.selectedProject = e.target.value;
    renderTasks();
    renderReports();
}

window.addEventListener('DOMContentLoaded', () => {
    renderProjectSelector();
    renderTasks();
    renderReports();
    console.log(dataTest.selectedProject);

    document.getElementById('projectSelector').addEventListener('change', handleProjectChange);

    document.getElementById('tasksTab').addEventListener('click', () => switchTab('tasks'));
    document.getElementById('reportsTab').addEventListener('click', () => switchTab('reports'));

    document.getElementById('createTaskBtn').addEventListener('click', () => openTaskModal());
    document.getElementById('closeTaskModal').addEventListener('click', closeTaskModal);
    document.getElementById('cancelTaskBtn').addEventListener('click', closeTaskModal);
    document.getElementById('taskForm').addEventListener('submit', handleTaskSubmit);

    document.getElementById('createReportBtn').addEventListener('click', openReportModal);
    document.getElementById('closeReportModal').addEventListener('click', closeReportModal);
    document.getElementById('cancelReportBtn').addEventListener('click', closeReportModal);
    document.getElementById('reportForm').addEventListener('submit', handleReportSubmit);
    document.getElementById('reportFile').addEventListener('change', handleFileUpload);

    document.getElementById('taskModal').addEventListener('click', function (e) {
        if (e.target === this) closeTaskModal();
    });

    document.getElementById('reportModal').addEventListener('click', function (e) {
        if (e.target === this) closeReportModal();
    });
});