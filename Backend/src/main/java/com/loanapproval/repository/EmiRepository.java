package com.loanapproval.repository;

import com.loanapproval.domain.EmiInstallment;
import com.loanapproval.domain.InstallmentStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface EmiRepository
        extends MongoRepository<EmiInstallment, UUID> {

    List<EmiInstallment> findByLoanAccountId(UUID loanAccountId);

    List<EmiInstallment> findByStatus(InstallmentStatus status);

    List<EmiInstallment> findByLoanAccountIdAndStatus(
            UUID loanAccountId,
            InstallmentStatus status
    );
}
