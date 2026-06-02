package com.loanapproval.service;

import com.loanapproval.model.ApiResponse;

import java.util.UUID;

public interface LoanDisbursementService {

    ApiResponse<String> disburseLoan(UUID loanId);
}
