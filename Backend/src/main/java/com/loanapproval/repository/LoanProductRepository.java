package com.loanapproval.repository;

import com.loanapproval.domain.LoanProduct;
import com.loanapproval.domain.LoanType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface LoanProductRepository
        extends MongoRepository<LoanProduct, UUID> {

    List<LoanProduct> findByActiveTrue();

    List<LoanProduct> findByLoanType(LoanType loanType);
}
