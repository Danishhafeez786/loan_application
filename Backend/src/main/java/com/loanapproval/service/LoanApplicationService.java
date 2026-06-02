package com.loanapproval.service;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.CreateLoanApplicationRequest;
import com.loanapproval.model.LoanApplicationResponse;
import com.loanapproval.model.UpdateLoanApplicationRequest;

import java.util.List;
import java.util.UUID;

public interface LoanApplicationService {

    ApiResponse<LoanApplicationResponse> createLoanApplication(
            CreateLoanApplicationRequest request);

    ApiResponse<List<LoanApplicationResponse>> getMyLoans();

    ApiResponse<LoanApplicationResponse> getLoanById(UUID id);

    ApiResponse<LoanApplicationResponse> updateLoan(
            UUID id,
            UpdateLoanApplicationRequest request);

    ApiResponse<String> deleteLoan(UUID id);

    ApiResponse<String> submitDraft(UUID id);
}
