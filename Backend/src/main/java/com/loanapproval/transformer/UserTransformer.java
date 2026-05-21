package com.loanapproval.transformer;

import com.loanapproval.domain.User;
import com.loanapproval.domain.UserRole;
import com.loanapproval.model.CreateEmployeeRequest;
import com.loanapproval.model.SignupRequest;
import com.loanapproval.model.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserTransformer {

    public User toEntity(SignupRequest request,
                         PasswordEncoder passwordEncoder) {

        return User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deleted(false)
                .active(true)
                .accountLocked(false)
                .build();
    }

    public UserResponse toResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .active(user.isActive())
                .build();
    }

    public User employeeToEntity(
            CreateEmployeeRequest request,
            PasswordEncoder passwordEncoder) {

        return User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deleted(false)
                .active(true)
                .accountLocked(false)
                .build();
    }
}
