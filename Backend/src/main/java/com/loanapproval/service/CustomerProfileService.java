package com.loanapproval.service;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.CreateCustomerProfileRequest;
import com.loanapproval.model.CustomerProfileResponse;
import com.loanapproval.model.UpdateCustomerProfileRequest;

public interface CustomerProfileService {

    ApiResponse<CustomerProfileResponse> createProfile(
            CreateCustomerProfileRequest request
    );

    ApiResponse<CustomerProfileResponse> getProfile();

    ApiResponse<CustomerProfileResponse> updateProfile(
            UpdateCustomerProfileRequest request
    );
}
