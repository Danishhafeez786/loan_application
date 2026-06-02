package com.loanapproval.transformer;

import com.loanapproval.domain.CustomerProfile;
import com.loanapproval.model.CreateCustomerProfileRequest;
import com.loanapproval.model.CustomerProfileResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerProfileTransformer {

    public CustomerProfile toEntity(CreateCustomerProfileRequest request) {

        return CustomerProfile.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .cnicNumber(request.getCnicNumber())
                .address(request.getAddress())
                .city(request.getCity())
                .province(request.getProvince())
                .country(request.getCountry())
                .postalCode(request.getPostalCode())
                .occupation(request.getOccupation())
                .employerName(request.getEmployerName())
                .monthlyIncome(request.getMonthlyIncome())
                .build();
    }

    public CustomerProfileResponse toResponse(CustomerProfile profile) {

        return CustomerProfileResponse.builder()
                .id(profile.getId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .gender(profile.getGender())
                .dateOfBirth(profile.getDateOfBirth())
                .cnicNumber(profile.getCnicNumber())
                .address(profile.getAddress())
                .city(profile.getCity())
                .province(profile.getProvince())
                .country(profile.getCountry())
                .postalCode(profile.getPostalCode())
                .occupation(profile.getOccupation())
                .employerName(profile.getEmployerName())
                .monthlyIncome(profile.getMonthlyIncome())
                .profilePictureUrl(profile.getProfilePictureUrl())
                .profileCompleted(profile.isProfileCompleted())
                .build();
    }
}
