package com.loanapproval.controller;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.LoanApplicationResponse;
import com.loanapproval.service.LoanManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/manager/loans")
@RequiredArgsConstructor
@PreAuthorize("hasRole('MANAGER')")
public class LoanManagerController {

    private final LoanManagerService service;

    // 1. Get loans for final review
    @GetMapping("/pending")
    public ApiResponse<List<LoanApplicationResponse>> getPendingLoans() {
        return service.getPendingLoans();
    }

    // 2. Approve loan
    @PostMapping("/{id}/approve")
    public ApiResponse<LoanApplicationResponse> approveLoan(
            @PathVariable UUID id,
            @RequestParam BigDecimal approvedAmount,
            @RequestParam BigDecimal interestRate) {

        return service.approveLoan(id, approvedAmount, interestRate);
    }

    // 3. Reject loan
    @PostMapping("/{id}/reject")
    public ApiResponse<String> rejectLoan(
            @PathVariable UUID id,
            @RequestParam String reason) {

        return service.rejectLoan(id, reason);
    }
}
