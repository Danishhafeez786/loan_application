package com.loanapproval.model;

import com.loanapproval.domain.Gender;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class CustomerProfileResponse {

    private UUID id;

    private String firstName;

    private String lastName;

    private Gender gender;

    private LocalDate dateOfBirth;

    private String cnicNumber;

    private String address;

    private String city;

    private String province;

    private String country;

    private String postalCode;

    private String occupation;

    private String employerName;

    private BigDecimal monthlyIncome;

    private String profilePictureUrl;

    private boolean profileCompleted;
}
