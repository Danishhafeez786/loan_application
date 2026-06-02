package com.loanapproval.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateLoanApplicationRequest {

    private BigDecimal requestedAmount;

    private Integer tenureMonths;

    private String loanPurpose;
}
