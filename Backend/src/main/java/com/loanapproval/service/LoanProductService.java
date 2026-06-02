package com.loanapproval.service;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.CreateLoanProductRequest;
import com.loanapproval.model.LoanProductResponse;
import com.loanapproval.model.UpdateLoanProductRequest;

import java.util.List;
import java.util.UUID;

public interface LoanProductService {
    ApiResponse<LoanProductResponse> createLoanProduct(
            CreateLoanProductRequest request);

    ApiResponse<List<LoanProductResponse>> getAllLoanProducts();

    ApiResponse<List<LoanProductResponse>> getActiveLoanProducts();

    ApiResponse<LoanProductResponse> getLoanProduct(
            UUID id);

    ApiResponse<LoanProductResponse> updateLoanProduct(
            UUID id,
            UpdateLoanProductRequest request);

    ApiResponse<String> deactivateLoanProduct(
            UUID id);
}
