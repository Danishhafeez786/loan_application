package com.loanapproval.model;

import com.loanapproval.domain.LoanStage;
import com.loanapproval.domain.LoanStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class LoanDetailsResponse {

    private UUID id;

    private String loanType;

    private BigDecimal requestedAmount;

    private BigDecimal approvedAmount;

    private BigDecimal interestRate;

    private Integer tenureMonths;

    private LoanStatus status;

    private LoanStage stage;

    private String remarks;

    private String rejectionReason;

    private LocalDateTime submittedAt;

    private LocalDateTime approvedAt;
}
