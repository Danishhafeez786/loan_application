package com.loanapproval.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class NotificationResponse {

    private UUID id;

    private String title;

    private String message;

    private boolean read;

    private LocalDateTime createdAt;
}
