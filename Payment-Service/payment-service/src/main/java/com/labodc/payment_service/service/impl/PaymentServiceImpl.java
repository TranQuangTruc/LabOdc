package com.labodc.payment_service.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final RabbitTemplate rabbitTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentServiceImpl(
            FundDistributionRepository fundDistributionRepository,
            ProjectPaymentRepository projectPaymentRepository,
            RabbitTemplate rabbitTemplate,
            KafkaTemplate<String, Object> kafkaTemplate) {
        this.fundDistributionRepository = fundDistributionRepository;
        this.projectPaymentRepository = projectPaymentRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.kafkaTemplate = kafkaTemplate;
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

        // 3. Response + publish events (RabbitMQ + Kafka)
        PaymentResponseDTO res = buildResponse(payment.getId(), dist, payment.getStatus());
        publishPaymentEvent(res);
        return res;
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

        PaymentResponseDTO res = buildResponse(payment.getId(), dist, payment.getStatus());
        publishPaymentEvent(res);
        return res;
    }

    private void publishPaymentEvent(PaymentResponseDTO res) {
        // RabbitMQ: exchange + routing key (can be bound to queues by other services)
        rabbitTemplate.convertAndSend("payment.exchange", "payment.created", res);
        // Kafka: topic
        kafkaTemplate.send("payment.created.v1", res.getPaymentId().toString(), res);
    }

    // ============================
    // Query
    // ============================
    @Override
    public PaymentResponseDTO getPaymentById(UUID id) {

        ProjectPayment payment = projectPaymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        FundDistribution dist = fundDistributionRepository.findByPaymentId(id);
        if (dist == null) {
            throw new RuntimeException("Distribution not found");
        }

        return buildResponse(payment.getId(), dist, payment.getStatus());
    }

    // ============================
    // Business logic chia tiền
    // ============================
    private FundDistribution calculateDistribution(BigDecimal total) {

        // Rule: 70% Team, 20% Mentor, 10% Lab
        // Assume VND (no decimals). We still guard with rounding.
        BigDecimal team = total.multiply(new BigDecimal("0.70")).setScale(0, RoundingMode.HALF_UP);
        BigDecimal mentor = total.multiply(new BigDecimal("0.20")).setScale(0, RoundingMode.HALF_UP);
        // Make sure sum exactly equals total (avoid rounding drift)
        BigDecimal lab = total.subtract(team).subtract(mentor);

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
