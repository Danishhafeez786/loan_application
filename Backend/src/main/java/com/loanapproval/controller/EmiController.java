package com.loanapproval.controller;


import com.loanapproval.model.ApiResponse;
import com.loanapproval.service.EmiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/customer/emi")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
public class EmiController {

    private final EmiService emiService;

    // 1️⃣ Get EMI Schedule
    @GetMapping("/{loanAccountId}")
    public ApiResponse<?> getEmiSchedule(
            @PathVariable UUID loanAccountId) {

        return emiService.getEmiSchedule(loanAccountId);
    }

    // 2️⃣ Pay EMI
    @PostMapping("/pay/{emiId}")
    public ApiResponse<String> payEmi(
            @PathVariable UUID emiId) {

        return emiService.payEmi(emiId);
    }

    // 3️⃣ Optional: Loan repayment summary
    @GetMapping("/summary/{loanAccountId}")
    public ApiResponse<?> getRepaymentSummary(
            @PathVariable UUID loanAccountId) {

        return emiService.getRepaymentSummary(loanAccountId);
    }
}
