package com.loanapproval.controller;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.CreateCustomerProfileRequest;
import com.loanapproval.model.CustomerProfileResponse;
import com.loanapproval.model.UpdateCustomerProfileRequest;
import com.loanapproval.service.CustomerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/profile")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerProfileController {

    private final CustomerProfileService service;

    @PostMapping
    public ApiResponse<CustomerProfileResponse> createProfile(
            @Valid @RequestBody CreateCustomerProfileRequest request) {

        return service.createProfile(request);
    }

    @GetMapping
    public ApiResponse<CustomerProfileResponse> getProfile() {

        return service.getProfile();
    }

    @PutMapping
    public ApiResponse<CustomerProfileResponse> updateProfile(
            @Valid @RequestBody UpdateCustomerProfileRequest request) {

        return service.updateProfile(request);
    }
}
