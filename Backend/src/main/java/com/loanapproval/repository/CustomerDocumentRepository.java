package com.loanapproval.repository;

import com.loanapproval.domain.CustomerDocument;
import com.loanapproval.domain.DocumentType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerDocumentRepository
        extends MongoRepository<CustomerDocument, UUID> {

    List<CustomerDocument> findByUserId(UUID userId);

    List<CustomerDocument> findByUserIdAndDocumentType(
            UUID userId,
            DocumentType documentType
    );
}
