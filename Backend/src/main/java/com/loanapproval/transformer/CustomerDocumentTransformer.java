package com.loanapproval.transformer;

import com.loanapproval.domain.CustomerDocument;
import com.loanapproval.model.DocumentResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerDocumentTransformer {

    public DocumentResponse toResponse(CustomerDocument doc) {

        return DocumentResponse.builder()
                .id(doc.getId())
                .documentType(doc.getDocumentType())
                .fileName(doc.getFileName())
                .fileUrl(doc.getFileUrl())
                .status(doc.getStatus())
                .remarks(doc.getRemarks())
                .uploadedAt(doc.getUploadedAt())
                .build();
    }
}