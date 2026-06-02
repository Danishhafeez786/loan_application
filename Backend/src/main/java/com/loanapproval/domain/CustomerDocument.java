package com.loanapproval.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "customer_documents")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDocument {

    @Id
    private UUID id;

    private UUID userId;

    private DocumentType documentType;

    private String fileName;

    private String fileUrl;

    private DocumentStatus status;

    private String remarks;

    private UUID verifiedBy;

    private LocalDateTime uploadedAt;
}
