package com.loanapproval.model;

import com.loanapproval.domain.LoanStage;
import com.loanapproval.domain.LoanStatus;
import com.loanapproval.domain.RiskLevel;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class LoanApplicationResponse {

    private UUID id;

    private UUID loanProductId;

    private String loanProductName;

    private BigDecimal requestedAmount;

    private Integer tenureMonths;

    private String loanPurpose;

    private LoanStatus status;

    private LoanStage stage;

    private BigDecimal approvedAmount;

    private Integer creditScore;

    private RiskLevel riskLevel;

    private LocalDateTime submittedAt;
}