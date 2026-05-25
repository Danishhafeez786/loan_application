package com.loanapproval.model;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignupRequest {


    private String fullName;

    @NotBlank(message = "Email Required")
    @Email
    private String email;

    @NotBlank(message = "Password Required")
    private String password;

}
