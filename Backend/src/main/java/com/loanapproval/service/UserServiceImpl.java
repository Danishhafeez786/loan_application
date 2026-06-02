package com.loanapproval.service;

import com.loanapproval.config.JwtUtil;
import com.loanapproval.domain.*;
import com.loanapproval.model.*;
import com.loanapproval.repository.*;
import com.loanapproval.transformer.UserTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserTransformer transformer;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetOtpRepository otpRepository;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final SecurityService  securityService;
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanAccountRepository loanAccountRepository;
    private final EmiRepository emiRepository;
    private final BlacklistedTokenRepository blacklistRepo;
        private final JwtService jwtService;

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

    @Override
    public ApiResponse<String> forgotPassword(ForgotPasswordRequest request) {

        User user = userRepository.findByEmailAndDeletedFalse(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String otp = String.valueOf((int)((Math.random() * 900000) + 100000));

        PasswordResetOtp passwordResetOtp = PasswordResetOtp.builder()
                .id(UUID.randomUUID())
                .email(user.getEmail())
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .verified(false)
                .build();

        otpRepository.save(passwordResetOtp);

        emailService.sendOtpEmail(user.getEmail(), otp);

        return ApiResponse.<String>builder()
                .success(true)
                .message("OTP sent successfully")
                .data(user.getEmail())
                .build();
    }

    @Override
    public ApiResponse<String> verifyOtp(VerifyOtpRequest request) {

        PasswordResetOtp otpData = otpRepository
                .findTopByEmailOrderByExpiryTimeDesc(request.getEmail())
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (otpData.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!otpData.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        otpData.setVerified(true);

        otpRepository.save(otpData);

        return ApiResponse.<String>builder()
                .success(true)
                .message("OTP verified successfully")
                .data(null)
                .build();
    }

    @Override
    public ApiResponse<String> resetPassword(ResetPasswordRequest request) {

        if (!request.getNewPassword()
                .equals(request.getConfirmPassword())) {

            throw new RuntimeException("Passwords do not match");
        }

        PasswordResetOtp otpData = otpRepository
                .findTopByEmailOrderByExpiryTimeDesc(request.getEmail())
                .orElseThrow(() -> new RuntimeException("OTP verification required"));

        if (!otpData.isVerified()) {
            throw new RuntimeException("OTP not verified");
        }

        User user = userRepository.findByEmailAndDeletedFalse(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        userRepository.save(user);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Password reset successful")
                .data(null)
                .build();
    }

    @Override
    public ApiResponse<List<CustomerLoanDashboardResponse>> getMyLoansDashboard() {

        User user = securityService.getCurrentUser();

        List<LoanApplication> applications =
                loanApplicationRepository.findByCustomerId(user.getId());

        List<CustomerLoanDashboardResponse> response =
                applications.stream().map(app -> {

                    LoanAccount account =
                            loanAccountRepository
                                    .findByLoanApplicationId(app.getId())
                                    .orElse(null);

                    List<EmiInstallment> emis =
                            account != null
                                    ? emiRepository.findByLoanAccountId(account.getId())
                                    : List.of();

                    long paid = emis.stream()
                            .filter(e -> e.getStatus() == InstallmentStatus.PAID)
                            .count();

                    long total = emis.size();

                    return CustomerLoanDashboardResponse.builder()
                            .loanApplicationId(app.getId())
                            .loanAccountId(account != null ? account.getId() : null) // 🔥 HERE
                            .accountNumber(account != null ? account.getAccountNumber() : null)
                            .loanStatus(app.getStatus().name())
                            .stage(app.getStage() != null ? app.getStage().name() : null)
                            .approvedAmount(app.getApprovedAmount())
                            .interestRate(app.getApprovedInterestRate())
                            .totalInstallments((int) total)
                            .paidInstallments((int) paid)
                            .pendingInstallments((int) (total - paid))
                            .build();

                }).toList();

        return ApiResponse.<List<CustomerLoanDashboardResponse>>builder()
                .success(true)
                .message("Dashboard fetched successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<String> logout(String token) {

        String cleanToken = token.replace("Bearer ", "");

        Date expiry = jwtService.extractExpiration(cleanToken);

        BlacklistedToken blacklistedToken = BlacklistedToken.builder()
                .token(cleanToken)
                .blacklistedAt(LocalDateTime.now())
                .expiresAt(expiry.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .build();

        blacklistRepo.save(blacklistedToken);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Logged out successfully")
                .data("LOGOUT_DONE")
                .build();
    }
}
