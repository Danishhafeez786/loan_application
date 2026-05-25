package com.loanapproval.service;

import com.loanapproval.config.JwtUtil;
import com.loanapproval.domain.User;
import com.loanapproval.model.*;
import com.loanapproval.repository.UserRepository;
import com.loanapproval.transformer.UserTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserTransformer transformer;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public ApiResponse<?> signup(SignupRequest request) {

//        if (!request.getPassword()
//                .equals(request.getReEnterPassword())) {
//
//            return ApiResponse.builder()
//                    .success(false)
//                    .message("Passwords do not match")
//                    .build();
//        }

        if (userRepository.existsByEmail(request.getEmail())) {

            return ApiResponse.builder()
                    .success(false)
                    .message("Email already exists")
                    .build();
        }

        User user = transformer.toEntity(request,
                passwordEncoder);

        userRepository.save(user);

        return ApiResponse.builder()
                .success(true)
                .message("Registration successful")
                .data(transformer.toResponse(user))
                .build();
    }

    @Override
    public ApiResponse<?> login(LoginRequest request) {

        User user = userRepository
                .findByEmailAndDeletedFalse(request.getEmail())
                .orElse(null);

        if (user == null) {

            return ApiResponse.builder()
                    .success(false)
                    .message("Invalid email")
                    .build();
        }

        if (!passwordEncoder.matches(request.getPassword(),
                user.getPassword())) {

            return ApiResponse.builder()
                    .success(false)
                    .message("Invalid password")
                    .build();
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name());

        LoginRespons response =
                LoginRespons.builder()
                        .token(token)
                        .user(transformer.toResponse(user))
                        .build();

        return ApiResponse.builder()
                .success(true)
                .message("Login successful")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<?> createEmployee(
            CreateEmployeeRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {

            return ApiResponse.builder()
                    .success(false)
                    .message("Email already exists")
                    .build();
        }

        User user = transformer.employeeToEntity(
                request,
                passwordEncoder);

        userRepository.save(user);

        return ApiResponse.builder()
                .success(true)
                .message("Employee created successfully")
                .data(transformer.toResponse(user))
                .build();
    }

    @Override
    public ApiResponse<?> getAllUsers() {

        List<User> users =
                userRepository.findAllByDeletedFalse();

        List<UserResponse> response =
                users.stream()
                        .map(transformer::toResponse)
                        .toList();

        return ApiResponse.builder()
                .success(true)
                .message("Users fetched successfully")
                .data(response)
                .build();
    }
}
