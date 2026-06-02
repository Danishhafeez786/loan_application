package com.loanapproval.repository;

import com.loanapproval.domain.LoanApplication;
import com.loanapproval.domain.LoanStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface LoanApplicationRepository
        extends MongoRepository<LoanApplication, UUID> {

    List<LoanApplication> findByCustomerId(UUID customerId);

    List<LoanApplication> findByCustomerIdAndStatus(
            UUID customerId,
            LoanStatus status
    );
}
