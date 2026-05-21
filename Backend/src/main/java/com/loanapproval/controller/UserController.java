package com.loanapproval.controller;

import com.loanapproval.config.JwtUtil;
import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.CreateEmployeeRequest;
import com.loanapproval.model.LoginRequest;
import com.loanapproval.model.SignupRequest;
import com.loanapproval.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ApiResponse<?> signup(
            @Valid @RequestBody SignupRequest request) {

        return userService.signup(request);
    }

    @PostMapping("/login")
    public ApiResponse<?> login(
            @Valid @RequestBody LoginRequest request) {

        return userService.login(request);
    }


}