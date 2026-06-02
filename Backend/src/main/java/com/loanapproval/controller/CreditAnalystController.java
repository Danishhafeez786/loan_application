package com.loanapproval.controller;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.LoanApplicationResponse;
import com.loanapproval.service.CreditAnalystService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/credit/loans")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CREDIT_ANALYST')")
public class CreditAnalystController {

    private final CreditAnalystService service;

    // 1. Get loans for analysis
    @GetMapping("/under-review")
    public ApiResponse<List<LoanApplicationResponse>> getUnderReviewLoans() {
        return service.getUnderReviewLoans();
    }

    // 2. Analyze loan
    @PostMapping("/{id}/analyze")
    public ApiResponse<LoanApplicationResponse> analyzeLoan(
            @PathVariable UUID id) {

        return service.analyzeLoan(id);
    }

    // 3. Forward to manager
    @PostMapping("/{id}/forward")
    public ApiResponse<String> forwardToManager(
            @PathVariable UUID id) {

        return service.forwardToManager(id);
    }
}
