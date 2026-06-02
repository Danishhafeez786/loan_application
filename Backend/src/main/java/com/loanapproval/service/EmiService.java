package com.loanapproval.service;

import com.loanapproval.model.ApiResponse;

import java.util.UUID;

public interface EmiService {

    ApiResponse<?> getEmiSchedule(UUID loanAccountId);

    ApiResponse<String> payEmi(UUID emiId);

    ApiResponse<?> getRepaymentSummary(UUID loanAccountId);
}