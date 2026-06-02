package com.loanapproval.controller;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.CreateLoanApplicationRequest;
import com.loanapproval.model.LoanApplicationResponse;
import com.loanapproval.model.UpdateLoanApplicationRequest;
import com.loanapproval.service.LoanApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer/loans")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
public class LoanApplicationController {

    private final LoanApplicationService service;

    // 1️⃣ CREATE LOAN (DRAFT or SUBMIT)
    @PostMapping
    public ApiResponse<LoanApplicationResponse> createLoan(
            @Valid @RequestBody CreateLoanApplicationRequest request) {

        return service.createLoanApplication(request);
    }

    // 2️⃣ SUBMIT DRAFT
    @PostMapping("/{id}/submit")
    public ApiResponse<String> submitDraft(
            @PathVariable UUID id) {

        return service.submitDraft(id);
    }

    // 3️⃣ GET ALL MY LOANS
    @GetMapping
    public ApiResponse<List<LoanApplicationResponse>> getMyLoans() {

        return service.getMyLoans();
    }

    // 4️⃣ GET SINGLE LOAN
    @GetMapping("/{id}")
    public ApiResponse<LoanApplicationResponse> getLoanById(
            @PathVariable UUID id) {

        return service.getLoanById(id);
    }

    // 5️⃣ UPDATE LOAN (ONLY DRAFT)
    @PutMapping("/{id}")
    public ApiResponse<LoanApplicationResponse> updateLoan(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateLoanApplicationRequest request) {

        return service.updateLoan(id, request);
    }

    // 6️⃣ DELETE LOAN (ONLY DRAFT)
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteLoan(
            @PathVariable UUID id) {

        return service.deleteLoan(id);
    }
}