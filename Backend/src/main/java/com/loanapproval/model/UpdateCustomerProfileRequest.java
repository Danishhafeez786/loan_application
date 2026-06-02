package com.loanapproval.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateCustomerProfileRequest {

    private String address;

    private Gender gender;

    private String city;

    private String province;

    private String country;

    private String postalCode;

    private String occupation;

    private String employerName;

    private BigDecimal monthlyIncome;
}