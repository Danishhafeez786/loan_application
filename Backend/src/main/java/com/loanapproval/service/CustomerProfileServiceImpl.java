package com.loanapproval.service;

import com.loanapproval.domain.CustomerProfile;
import com.loanapproval.domain.User;
import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.CreateCustomerProfileRequest;
import com.loanapproval.model.CustomerProfileResponse;
import com.loanapproval.model.UpdateCustomerProfileRequest;
import com.loanapproval.repository.CustomerProfileRepository;
import com.loanapproval.transformer.CustomerProfileTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerProfileServiceImpl
        implements CustomerProfileService {

    private final CustomerProfileRepository repository;

    private final CustomerProfileTransformer transformer;

    private final SecurityService securityService;

    @Override
    public ApiResponse<CustomerProfileResponse> createProfile(
            CreateCustomerProfileRequest request) {

        User user = securityService.getCurrentUser();

        if (repository.existsByUserId(user.getId())){
            throw new RuntimeException("Profile already exists");
        }

        CustomerProfile profile =
                transformer.toEntity(request);

        profile.setId(user.getId());

        profile.setUserId(user.getId());

        profile.setProfileCompleted(true);

        profile.setCreatedAt(LocalDateTime.now());

        repository.save(profile);

        return ApiResponse.<CustomerProfileResponse>builder()
                .success(true)
                .message("Profile created successfully")
                .data(transformer.toResponse(profile))
                .build();
    }

    @Override
    public ApiResponse<CustomerProfileResponse> getProfile() {

        User user = securityService.getCurrentUser();

        CustomerProfile profile =
                repository.findByUserId(user.getId())
                        .orElseThrow(() ->
                                new RuntimeException("Profile not found"));

        return ApiResponse.<CustomerProfileResponse>builder()
                .success(true)
                .message("Profile fetched successfully")
                .data(transformer.toResponse(profile))
                .build();
    }

    @Override
    public ApiResponse<CustomerProfileResponse> updateProfile(
            UpdateCustomerProfileRequest request) {

        User user = securityService.getCurrentUser();

        CustomerProfile profile =
                repository.findByUserId(user.getId())
                        .orElseThrow(() ->
                                new RuntimeException("Profile not found"));

        profile.setAddress(request.getAddress());
        profile.setCity(request.getCity());
        profile.setProvince(request.getProvince());
        profile.setCountry(request.getCountry());
        profile.setPostalCode(request.getPostalCode());
        profile.setOccupation(request.getOccupation());
        profile.setEmployerName(request.getEmployerName());
        profile.setMonthlyIncome(request.getMonthlyIncome());

        repository.save(profile);

        return ApiResponse.<CustomerProfileResponse>builder()
                .success(true)
                .message("Profile updated successfully")
                .data(transformer.toResponse(profile))
                .build();
    }
}
