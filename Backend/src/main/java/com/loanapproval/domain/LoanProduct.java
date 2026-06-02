package com.loanapproval.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "loan_products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanProduct {

    @Id
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

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
