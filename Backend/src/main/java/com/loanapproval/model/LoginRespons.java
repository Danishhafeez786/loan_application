package com.loanapproval.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRespons {
    private String token;

    private UserResponse user;
}
