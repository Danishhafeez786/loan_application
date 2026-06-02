package com.loanapproval.controller;

import com.loanapproval.config.JwtUtil;
import com.loanapproval.model.*;
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
    public ApiResponse<?> signup(@Valid @RequestBody SignupRequest request) {

        return userService.signup(request);
    }

    @PostMapping("/signin")
    public ApiResponse<?> login(
            @Valid @RequestBody LoginRequest request) {

        return userService.login(request);
    }

    //Forget Password//

    @PostMapping("/forgot-password")
    public ApiResponse<String> forgotPassword(
            @RequestBody ForgotPasswordRequest request
    ) {
        return userService.forgotPassword(request);
    }

    @PostMapping("/verify-otp")
    public ApiResponse<String> verifyOtp(
            @RequestBody VerifyOtpRequest request
    ) {
        return userService.verifyOtp(request);
    }

    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        return userService.resetPassword(request);
    }

    @GetMapping("/loans")
    public ApiResponse<?> getDashboard() {
        return userService.getMyLoansDashboard();
    }


    @PostMapping("/logout")
    public ApiResponse<String> logout(
            @RequestHeader("Authorization") String token) {

        return userService.logout(token);
    }

}