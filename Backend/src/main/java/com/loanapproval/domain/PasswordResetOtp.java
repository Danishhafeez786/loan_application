package com.loanapproval.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "password_reset_otps")
public class PasswordResetOtp {

    @Id
    private UUID id;

    private String email;

    private String otp;

    private LocalDateTime expiryTime;

    private boolean verified;
}
