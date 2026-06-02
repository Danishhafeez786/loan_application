package com.loanapproval.repository;

import com.loanapproval.domain.LoanAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanAccountRepository
        extends MongoRepository<LoanAccount, UUID> {

    List<LoanAccount> findByCustomerId(UUID customerId);


    Optional<LoanAccount> findByLoanApplicationId(UUID loanApplicationId);
}