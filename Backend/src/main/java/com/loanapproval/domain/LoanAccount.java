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
@Document(collection = "loan_accounts")
public class LoanAccount {
    @Id
    private UUID id;

    private UUID loanApplicationId;

    private UUID customerId;

    private String accountNumber;

    private BigDecimal principalAmount;

    private BigDecimal interestRate;

    private Integer tenureMonths;

    private LocalDate disbursementDate;

    private LoanAccountStatus status;
}
