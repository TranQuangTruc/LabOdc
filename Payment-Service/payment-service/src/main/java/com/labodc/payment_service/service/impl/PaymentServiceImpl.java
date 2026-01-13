package com.labodc.payment_service.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.labodc.payment_service.dto.FundDistributionDTO;
import com.labodc.payment_service.dto.PaymentRequestDTO;
import com.labodc.payment_service.dto.PaymentResponseDTO;
import com.labodc.payment_service.entity.FundDistribution;
import com.labodc.payment_service.entity.PaymentStatus;
import com.labodc.payment_service.entity.ProjectPayment;
import com.labodc.payment_service.repository.FundDistributionRepository;
import com.labodc.payment_service.repository.ProjectPaymentRepository;
import com.labodc.payment_service.service.PaymentService;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final FundDistributionRepository fundDistributionRepository;
    private final ProjectPaymentRepository projectPaymentRepository;

    public PaymentServiceImpl(
            FundDistributionRepository fundDistributionRepository,
            ProjectPaymentRepository projectPaymentRepository) {
        this.fundDistributionRepository = fundDistributionRepository;
        this.projectPaymentRepository = projectPaymentRepository;
    }

    // ============================
    // Enterprise pays for project
    // ============================
    @Override
    public PaymentResponseDTO processEnterprisePayment(PaymentRequestDTO request) {

        // 1. Tạo ProjectPayment
        ProjectPayment payment = new ProjectPayment();
        payment.setProjectId(request.getProjectId());
        payment.setTotalAmount(request.getAmount());
        payment.setStatus(PaymentStatus.PAID);
        payment.setAdvancedByLab(false);
        payment.setCreatedAt(LocalDateTime.now());

        projectPaymentRepository.save(payment);

        // 2. Chia tiền
        FundDistribution dist = calculateDistribution(request.getAmount());
        dist.setPaymentId(payment.getId());

        fundDistributionRepository.save(dist);

        // 3. Response
        return buildResponse(payment.getId(), dist, payment.getStatus());
    }

    // ============================
    // Lab ứng tiền cho project
    // ============================
    @Override
    public PaymentResponseDTO advancePaymentByLab(PaymentRequestDTO request) {

        ProjectPayment payment = new ProjectPayment();
        payment.setProjectId(request.getProjectId());
        payment.setTotalAmount(request.getAmount());
        payment.setStatus(PaymentStatus.ADVANCED_BY_LAB);
        payment.setAdvancedByLab(true);
        payment.setCreatedAt(LocalDateTime.now());

        projectPaymentRepository.save(payment);

        FundDistribution dist = calculateDistribution(request.getAmount());
        dist.setPaymentId(payment.getId());

        fundDistributionRepository.save(dist);

        return buildResponse(payment.getId(), dist, payment.getStatus());
    }

    // ============================
    // Query
    // ============================
    @Override
    public PaymentResponseDTO getPaymentById(UUID id) {

        ProjectPayment payment = projectPaymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        FundDistribution dist = fundDistributionRepository
                .findAll()
                .stream()
                .filter(d -> d.getPaymentId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Distribution not found"));

        return buildResponse(payment.getId(), dist, payment.getStatus());
    }

    // ============================
    // Business logic chia tiền
    // ============================
    private FundDistribution calculateDistribution(BigDecimal total) {

        BigDecimal team = total.multiply(new BigDecimal("0.6"));
        BigDecimal mentor = total.multiply(new BigDecimal("0.2"));
        BigDecimal lab = total.multiply(new BigDecimal("0.2"));

        FundDistribution d = new FundDistribution();
        d.setTeamAmount(team);
        d.setMentorAmount(mentor);
        d.setLabAmount(lab);

        return d;
    }

    // ============================
    // Mapper
    // ============================
    private PaymentResponseDTO buildResponse(UUID paymentId, FundDistribution dist, PaymentStatus status) {

        FundDistributionDTO dto = new FundDistributionDTO();
        dto.setTeam(dist.getTeamAmount());
        dto.setMentor(dist.getMentorAmount());
        dto.setLab(dist.getLabAmount());

        PaymentResponseDTO res = new PaymentResponseDTO();
        res.setPaymentId(paymentId);
        res.setDistribution(dto);
        res.setStatus(status.name());

        return res;
    }
}
