package com.loanapproval.model;

import com.loanapproval.domain.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private String id;
    private String fullName;
    private String email;
    private UserRole role;
    private LocalDateTime createdAt;
    private boolean active;
}
