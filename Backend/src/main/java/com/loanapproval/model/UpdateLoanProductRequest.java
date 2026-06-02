package com.loanapproval.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateLoanProductRequest {

    private String name;

    private String description;

    private BigDecimal minAmount;

    private BigDecimal maxAmount;

    private Integer minTenureMonths;

    private Integer maxTenureMonths;

    private BigDecimal interestRate;

    private BigDecimal processingFee;

    private Boolean active;
}
