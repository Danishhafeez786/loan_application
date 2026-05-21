package com.loanapproval.model;

import com.loanapproval.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateEmployeeRequest {

    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private UserRole role;
}
