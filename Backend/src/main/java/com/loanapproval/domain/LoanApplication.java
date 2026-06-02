package com.loanapproval.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "loan_applications")
public class LoanApplication {

    @Id
    private UUID id;

    private UUID customerId;

    private UUID loanProductId;

    private BigDecimal requestedAmount;

    private Integer tenureMonths;

    private String loanPurpose;


    private LoanStatus status;


    private LoanStage stage;


    private BigDecimal approvedAmount;

    private BigDecimal approvedInterestRate;


    private Integer creditScore;

    private RiskLevel riskLevel;


    private String remarks;

    private String rejectionReason;


    private UUID assignedOfficerId;

    private UUID approvedBy;

    private LocalDateTime submittedAt;

    private LocalDateTime reviewedAt;

    private LocalDateTime approvedAt;

    private LocalDateTime rejectedAt;

    private LocalDateTime disbursedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    private Boolean isDraft;
}
