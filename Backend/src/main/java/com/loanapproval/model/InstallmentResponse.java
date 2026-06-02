package com.loanapproval.model;

import com.loanapproval.domain.InstallmentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class InstallmentResponse {

    private Integer installmentNumber;

    private LocalDate dueDate;

    private BigDecimal amount;

    private InstallmentStatus status;
}