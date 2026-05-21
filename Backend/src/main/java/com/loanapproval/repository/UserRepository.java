package com.loanapproval.repository;

import com.loanapproval.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmailAndDeletedFalse(String email);

    boolean existsByEmail(String email);

    List<User> findAllByDeletedFalse();
}