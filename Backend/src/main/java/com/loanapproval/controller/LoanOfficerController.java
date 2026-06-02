package com.loanapproval.controller;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.LoanApplicationResponse;
import com.loanapproval.service.LoanOfficerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/officer/loans")
@RequiredArgsConstructor
@PreAuthorize("hasRole('LOAN_OFFICER')")
public class LoanOfficerController {

    private final LoanOfficerService service;

    // 1. Get all submitted loans
    @GetMapping("/submitted")
    public ApiResponse<List<LoanApplicationResponse>> getSubmittedLoans() {
        return service.getSubmittedLoans();
    }

    // 2. Get loan details
    @GetMapping("/{id}")
    public ApiResponse<LoanApplicationResponse> getLoan(@PathVariable UUID id) {
        return service.getLoanById(id);
    }

    // 3. Verify documents
    @PostMapping("/{id}/verify-documents")
    public ApiResponse<String> verifyDocuments(@PathVariable UUID id) {
        return service.verifyDocuments(id);
    }

    // 4. Request document correction
    @PostMapping("/{id}/request-correction")
    public ApiResponse<String> requestCorrection(
            @PathVariable UUID id,
            @RequestParam String message) {

        return service.requestCorrection(id, message);
    }

    // 5. Forward to credit analyst
    @PostMapping("/{id}/forward")
    public ApiResponse<String> forwardToCredit(@PathVariable UUID id) {
        return service.forwardToCredit(id);
    }
}
