package com.loanapproval.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateLoanApplicationRequest {

    @NotNull
    private UUID loanProductId;

    @NotNull
    @DecimalMin("1000")
    private BigDecimal requestedAmount;

    @NotNull
    @Min(1)
    private Integer tenureMonths;

    @NotBlank
    private String loanPurpose;

    private Boolean saveAsDraft;
}