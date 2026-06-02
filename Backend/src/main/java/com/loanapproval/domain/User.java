package com.loanapproval.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private UUID id = UUID.randomUUID();

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private UserRole role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    private boolean deleted = false;

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private boolean accountLocked = false;
}
