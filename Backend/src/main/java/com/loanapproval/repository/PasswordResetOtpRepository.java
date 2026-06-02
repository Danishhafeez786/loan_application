package com.loanapproval.repository;

import com.loanapproval.domain.PasswordResetOtp;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetOtpRepository
        extends MongoRepository<PasswordResetOtp, UUID> {

    Optional<PasswordResetOtp> findTopByEmailOrderByExpiryTimeDesc(String email);
}
