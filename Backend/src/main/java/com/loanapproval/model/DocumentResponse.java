package com.loanapproval.model;

import com.loanapproval.domain.DocumentStatus;
import com.loanapproval.domain.DocumentType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class DocumentResponse {

    private UUID id;

    private DocumentType documentType;

    private String fileName;

    private String fileUrl;

    private DocumentStatus status;

    private String remarks;

    private LocalDateTime uploadedAt;
}