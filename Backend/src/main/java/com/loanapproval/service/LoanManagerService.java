package com.loanapproval.service;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.LoanApplicationResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LoanManagerService {

    ApiResponse<List<LoanApplicationResponse>> getPendingLoans();

    ApiResponse<LoanApplicationResponse> approveLoan(
            UUID id,
            BigDecimal approvedAmount,
            BigDecimal interestRate);

    ApiResponse<String> rejectLoan(
            UUID id,
            String reason);
}
