package com.loanapproval.model;

import com.loanapproval.domain.LoanType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class LoanProductResponse {

    private UUID id;

    private String name;

    private String description;

    private LoanType loanType;

    private BigDecimal minAmount;

    private BigDecimal maxAmount;

    private Integer minTenureMonths;

    private Integer maxTenureMonths;

    private BigDecimal interestRate;

    private BigDecimal processingFee;
}