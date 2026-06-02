package com.loanapproval.service;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.DocumentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CustomerDocumentService {

    ApiResponse<DocumentResponse> uploadDocument(
            MultipartFile file,
            String documentType
    );

    ApiResponse<List<DocumentResponse>> getMyDocuments();

    ApiResponse<String> deleteDocument(UUID id);
}