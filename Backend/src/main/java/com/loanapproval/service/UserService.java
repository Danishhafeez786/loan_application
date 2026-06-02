package com.loanapproval.service;

import com.loanapproval.domain.CustomerLoanDashboardResponse;
import com.loanapproval.model.*;

import java.util.List;

public interface UserService {
    ApiResponse<?> signup(SignupRequest request);

    ApiResponse<?> login(LoginRequest request);

    ApiResponse<?> createEmployee(CreateEmployeeRequest request);

    ApiResponse<?> getAllUsers();

    ApiResponse<String> forgotPassword(ForgotPasswordRequest request);
    ApiResponse<String> verifyOtp(VerifyOtpRequest request);
    ApiResponse<String> resetPassword(ResetPasswordRequest request);

    ApiResponse<List<CustomerLoanDashboardResponse>> getMyLoansDashboard();

    ApiResponse<String> logout(String token);

}
