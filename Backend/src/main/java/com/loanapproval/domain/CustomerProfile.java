package com.loanapproval.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "customer_profiles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProfile {

    @Id
    private UUID id;

    private UUID userId;

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

    private String profilePictureUrl;

    private String occupation;

    private String employerName;

    private BigDecimal monthlyIncome;

    private boolean profileCompleted;

    private LocalDateTime createdAt;
}