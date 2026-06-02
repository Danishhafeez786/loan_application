package com.loanapproval.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "emi_installments")
public class EmiInstallment {

    @Id
    private UUID id;

    private UUID loanAccountId;

    private Integer installmentNumber;

    private BigDecimal amount;

    private BigDecimal principalComponent;

    private BigDecimal interestComponent;

    private LocalDate dueDate;

    private InstallmentStatus status;
}
