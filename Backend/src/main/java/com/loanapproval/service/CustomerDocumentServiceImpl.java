package com.loanapproval.service;

import com.loanapproval.domain.*;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.DocumentResponse;
import com.loanapproval.repository.CustomerDocumentRepository;
import com.loanapproval.transformer.CustomerDocumentTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerDocumentServiceImpl implements CustomerDocumentService {

    private final CustomerDocumentRepository repository;
    private final SecurityService securityService;
    private final CustomerDocumentTransformer transformer;

    private final String UPLOAD_DIR = "C:\\Users\\Tehseen\\Documents\\loan_application\\Backend\\uploads\\";

    @Override
    public ApiResponse<DocumentResponse> uploadDocument(
            MultipartFile file,
            String documentType) {

        try {
            User user = securityService.getCurrentUser();

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File destination = new File(UPLOAD_DIR + fileName);
            file.transferTo(destination);

            CustomerDocument doc = CustomerDocument.builder()
                    .id(UUID.randomUUID())
                    .userId(user.getId())
                    .documentType(DocumentType.valueOf(documentType))
                    .fileName(fileName)
                    .fileUrl(destination.getPath())
                    .status(DocumentStatus.PENDING)
                    .uploadedAt(LocalDateTime.now())
                    .build();

            repository.save(doc);

            return ApiResponse.<DocumentResponse>builder()
                    .success(true)
                    .message("Document uploaded successfully")
                    .data(transformer.toResponse(doc))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ApiResponse<List<DocumentResponse>> getMyDocuments() {

        User user = securityService.getCurrentUser();

        List<DocumentResponse> docs = repository
                .findByUserId(user.getId())
                .stream()
                .map(transformer::toResponse)
                .collect(Collectors.toList());

        return ApiResponse.<List<DocumentResponse>>builder()
                .success(true)
                .message("Documents fetched successfully")
                .data(docs)
                .build();
    }

    @Override
    public ApiResponse<String> deleteDocument(UUID id) {

        User user = securityService.getCurrentUser();

        CustomerDocument doc = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        if (!doc.getUserId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        repository.delete(doc);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Document deleted successfully")
                .data(null)
                .build();
    }
}
