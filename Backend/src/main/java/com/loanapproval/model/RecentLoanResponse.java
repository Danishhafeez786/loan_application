package com.loanapproval.model;

import com.loanapproval.domain.LoanStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class RecentLoanResponse {

    private UUID id;

    private String loanType;

    private BigDecimal amount;

    private LoanStatus status;

    private LocalDateTime submittedAt;
}