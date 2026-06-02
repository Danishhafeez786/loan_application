package com.loanapproval.controller;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.service.LoanDisbursementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/manager/disbursement")
@RequiredArgsConstructor
@PreAuthorize("hasRole('MANAGER')")
public class LoanDisbursementController {

    private final LoanDisbursementService service;

    @PostMapping("/{loanId}/disburse")
    public ApiResponse<String> disburseLoan(
            @PathVariable UUID loanId) {

        return service.disburseLoan(loanId);
    }
}
