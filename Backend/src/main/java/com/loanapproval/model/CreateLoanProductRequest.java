package com.loanapproval.model;

import com.loanapproval.domain.LoanType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateLoanProductRequest {

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
