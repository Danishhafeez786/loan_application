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
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "installments")
public class Installment {

    @Id
    private UUID id;

    private UUID loanAccountId;

    private Integer installmentNumber;

    private LocalDate dueDate;

    private BigDecimal installmentAmount;

    private BigDecimal paidAmount;

    private BigDecimal remainingAmount;

    private InstallmentStatus status;

    private LocalDate paymentDate;
}
