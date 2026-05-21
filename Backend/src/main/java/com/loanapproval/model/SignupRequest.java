package com.loanapproval.model;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank(message = "Full Name  Required")
    private String fullName;

    @NotBlank(message = "Email Required")
    @Email
    private String email;

    @NotBlank(message = "Password Required")
    private String password;

    @NotBlank(message = "Re Enter Password Required")
    private String reEnterPassword;
}
