package com.loanapproval.service;

import com.loanapproval.domain.User;
import com.loanapproval.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserRepository userRepository;

    public User getCurrentUser() {

        String email =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        return userRepository.findByEmailAndDeletedFalse(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
}
