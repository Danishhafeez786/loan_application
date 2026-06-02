package com.loanapproval.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoanDashboardResponse {

    private UUID loanApplicationId;

    private UUID loanAccountId;

    private String accountNumber;

    private String loanStatus;

    private String stage;

    private BigDecimal approvedAmount;

    private BigDecimal interestRate;

    private Integer totalInstallments;

    private Integer paidInstallments;

    private Integer pendingInstallments;
}
