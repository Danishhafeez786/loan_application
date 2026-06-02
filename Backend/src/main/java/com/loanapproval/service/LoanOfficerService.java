package com.loanapproval.service;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.LoanApplicationResponse;

import java.util.List;
import java.util.UUID;

public interface LoanOfficerService {

    ApiResponse<List<LoanApplicationResponse>> getSubmittedLoans();

    ApiResponse<LoanApplicationResponse> getLoanById(UUID id);

    ApiResponse<String> verifyDocuments(UUID id);

    ApiResponse<String> requestCorrection(UUID id, String message);

    ApiResponse<String> forwardToCredit(UUID id);
}