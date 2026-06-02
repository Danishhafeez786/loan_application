package com.loanapproval.model;

import com.loanapproval.domain.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserResponse {
    private UUID id;
    private String fullName;
    private String email;
    private UserRole role;
    private LocalDateTime createdAt;
    private boolean active;
}
