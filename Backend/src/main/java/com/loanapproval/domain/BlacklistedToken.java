package com.loanapproval.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "blacklisted_tokens")
public class BlacklistedToken {

    @Id
    private String token;

    private LocalDateTime blacklistedAt;

    private LocalDateTime expiresAt;
}
