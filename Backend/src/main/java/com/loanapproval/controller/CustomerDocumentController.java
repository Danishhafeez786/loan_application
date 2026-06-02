package com.loanapproval.controller;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.DocumentResponse;
import com.loanapproval.service.CustomerDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer/documents")
@RequiredArgsConstructor
public class CustomerDocumentController {

    private final CustomerDocumentService service;

    @PostMapping("/upload")
    public ApiResponse<DocumentResponse> upload(
            @RequestParam MultipartFile file,
            @RequestParam String documentType
    ) {
        return service.uploadDocument(file, documentType);
    }

    @GetMapping
    public ApiResponse<List<DocumentResponse>> getAll() {
        return service.getMyDocuments();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable UUID id) {
        return service.deleteDocument(id);
    }
}