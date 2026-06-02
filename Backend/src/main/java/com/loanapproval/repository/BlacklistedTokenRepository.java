package com.loanapproval.repository;

import com.loanapproval.domain.BlacklistedToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlacklistedTokenRepository
        extends MongoRepository<BlacklistedToken, String> {

    boolean existsByToken(String token);
}