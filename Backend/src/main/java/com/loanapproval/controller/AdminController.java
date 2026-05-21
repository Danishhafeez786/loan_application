package com.loanapproval.controller;

import com.loanapproval.config.JwtUtil;
import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.CreateEmployeeRequest;
import com.loanapproval.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

//    @PostMapping("/create-employee")
//    public ApiResponse<?> createEmployee(
//            @Valid @RequestBody CreateEmployeeRequest request) {
//
//        return userService.createEmployee(request);
//    }

    @PostMapping("/create-employee")
    public ApiResponse<?> createEmployee(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateEmployeeRequest request) {

        token = token.replace("Bearer ", "");

        String role = jwtUtil.extractRole(token);

        if (!"ADMIN".equals(role)) {
            return ApiResponse.builder()
                    .success(false)
                    .message("Access denied: Admin only")
                    .build();
        }

        return userService.createEmployee(request);
    }

    @GetMapping("/users")
    public ApiResponse<?> getAllUsers() {

        return userService.getAllUsers();
    }

    @GetMapping("/profile")
    public String profile(
            @RequestHeader("Authorization")
            String token) {

        token = token.replace("Bearer ", "");

        if (!jwtUtil.validateToken(token)) {

            return "Invalid token";
        }

        String email =
                jwtUtil.extractEmail(token);

        return "Welcome " + email;
    }
}
