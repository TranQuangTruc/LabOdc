package com.labodc.payment_service.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.labodc.payment_service.dto.FundDistributionDTO;
import com.labodc.payment_service.dto.PaymentRequestDTO;
import com.labodc.payment_service.dto.PaymentResponseDTO;
import com.labodc.payment_service.entity.FundDistribution;
import com.labodc.payment_service.entity.LabFund;
import com.labodc.payment_service.entity.PaymentStatus;
import com.labodc.payment_service.entity.ProjectPayment;
import com.labodc.payment_service.repository.FundDistributionRepository;
import com.labodc.payment_service.repository.LabFundRepository;
import com.labodc.payment_service.repository.ProjectPaymentRepository;
import com.labodc.payment_service.service.PaymentService;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final ProjectPaymentRepository paymentRepository;
    private final FundDistributionRepository distributionRepository;
    private final LabFundRepository labFundRepository;

    public PaymentServiceImpl(ProjectPaymentRepository paymentRepository,
                              FundDistributionRepository distributionRepository,
                              LabFundRepository labFundRepository) {
        this.paymentRepository = paymentRepository;
        this.distributionRepository = distributionRepository;
        this.labFundRepository = labFundRepository;
    }

    /**
     * Enterprise thanh toán bình thường
     */
    @Override
    public PaymentResponseDTO processEnterprisePayment(PaymentRequestDTO request) {

        ProjectPayment payment = createBasePayment(request);
        payment.setStatus(PaymentStatus.PAID);
        payment.setAdvancedByLab(false);

        paymentRepository.save(payment);

        FundDistribution distribution = createDistribution(payment);

        distributionRepository.save(distribution);

        return buildResponse(payment, distribution);
    }

    /**
     * Lab tạm ứng khi enterprise chưa trả
     */
    @Override
    public PaymentResponseDTO advancePaymentByLab(PaymentRequestDTO request) {

        LabFund labFund = labFundRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Lab fund not initialized"));

        if (labFund.getBalance().compareTo(request.getTotalAmount()) < 0) {
            throw new RuntimeException("Lab fund is insufficient for advance payment");
        }

        // Trừ tiền Lab
        labFund.setBalance(labFund.getBalance().subtract(request.getTotalAmount()));
        labFundRepository.save(labFund);

        ProjectPayment payment = createBasePayment(request);
        payment.setStatus(PaymentStatus.ADVANCED_BY_LAB);
        payment.setAdvancedByLab(true);

        paymentRepository.save(payment);

        FundDistribution distribution = createDistribution(payment);
        distributionRepository.save(distribution);

        return buildResponse(payment, distribution);
    }

    /* ================== PRIVATE METHODS ================== */

    private ProjectPayment createBasePayment(PaymentRequestDTO request) {
        ProjectPayment payment = new ProjectPayment();
        payment.setProjectId(request.getProjectId());
        payment.setEnterpriseId(request.getEnterpriseId());
        payment.setTotalAmount(request.getTotalAmount());
        payment.setCreatedAt(LocalDateTime.now());
        return payment;
    }

    private FundDistribution createDistribution(ProjectPayment payment) {
        BigDecimal total = payment.getTotalAmount();

        FundDistribution distribution = new FundDistribution();
        distribution.setPaymentId(payment.getId());
        distribution.setTeamAmount(calculatePercent(total, 70));
        distribution.setMentorAmount(calculatePercent(total, 20));
        distribution.setLabAmount(calculatePercent(total, 10));

        return distribution;
    }

    private BigDecimal calculatePercent(BigDecimal total, int percent) {
        return total.multiply(BigDecimal.valueOf(percent))
                    .divide(BigDecimal.valueOf(100));
    }

    private PaymentResponseDTO buildResponse(ProjectPayment payment,
                                             FundDistribution distribution) {

        FundDistributionDTO fundDTO = new FundDistributionDTO();
        fundDTO.setTeam(distribution.getTeamAmount());
        fundDTO.setMentor(distribution.getMentorAmount());
        fundDTO.setLab(distribution.getLabAmount());

        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setPaymentId(payment.getId());
        response.setDistribution(fundDTO);
        response.setStatus(payment.getStatus().name());

        return response;
    }
}
