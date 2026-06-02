package com.loanapproval.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class RepaymentScheduleResponse {

    private UUID loanId;

    private BigDecimal monthlyInstallment;

    private BigDecimal totalInterest;

    private BigDecimal totalPayableAmount;

    private Integer totalInstallments;

    private List<InstallmentResponse> installments;
}