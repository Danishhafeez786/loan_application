package com.loanapproval.controller;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.CreateLoanProductRequest;
import com.loanapproval.model.LoanProductResponse;
import com.loanapproval.model.UpdateLoanProductRequest;
import com.loanapproval.service.LoanProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/loan-products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminLoanProductController {

    private final LoanProductService service;

    @PostMapping
    public ApiResponse<LoanProductResponse> create(
            @RequestBody CreateLoanProductRequest request) {

        return service.createLoanProduct(request);
    }

    @GetMapping
    public ApiResponse<List<LoanProductResponse>> getAll() {

        return service.getAllLoanProducts();
    }

    @GetMapping("/{id}")
    public ApiResponse<LoanProductResponse> getById(
            @PathVariable UUID id) {

        return service.getLoanProduct(id);
    }

    @PutMapping("/{id}")
    public ApiResponse<LoanProductResponse> update(
            @PathVariable UUID id,
            @RequestBody UpdateLoanProductRequest request) {

        return service.updateLoanProduct(id, request);
    }

    @PatchMapping("/{id}/disable")
    public ApiResponse<String> disable(
            @PathVariable UUID id) {

        return service.deactivateLoanProduct(id);
    }


}
