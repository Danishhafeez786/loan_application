package com.loanapproval.service;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.CreateEmployeeRequest;
import com.loanapproval.model.LoginRequest;
import com.loanapproval.model.SignupRequest;

public interface UserService {
    ApiResponse<?> signup(SignupRequest request);

    ApiResponse<?> login(LoginRequest request);

    ApiResponse<?> createEmployee(CreateEmployeeRequest request);

    ApiResponse<?> getAllUsers();

}
