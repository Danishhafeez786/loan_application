package com.loanapproval.service;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.LoanApplicationResponse;

import java.util.List;
import java.util.UUID;

public interface CreditAnalystService {

    ApiResponse<List<LoanApplicationResponse>> getUnderReviewLoans();

    ApiResponse<LoanApplicationResponse> analyzeLoan(UUID id);

    ApiResponse<String> forwardToManager(UUID id);
}
