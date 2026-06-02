package com.loanapproval.repository;

import com.loanapproval.domain.CustomerProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerProfileRepository
        extends MongoRepository<CustomerProfile, UUID> {

    Optional<CustomerProfile> findByUserId(UUID userId);

    boolean existsByUserId(UUID userId);
}