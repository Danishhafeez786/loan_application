package com.loanapproval.service;

import com.loanapproval.domain.*;
import com.loanapproval.model.ApiResponse;
import com.loanapproval.repository.EmiRepository;
import com.loanapproval.repository.LoanAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmiServiceImpl implements EmiService {

    private final EmiRepository emiRepository;
    private final LoanAccountRepository loanAccountRepository;
    private final SecurityService securityService;

    // =========================
    // 1. GET EMI SCHEDULE
    // =========================
    @Override
    public ApiResponse<?> getEmiSchedule(UUID loanAccountId) {

        User user = securityService.getCurrentUser();

        LoanAccount account = loanAccountRepository.findById(loanAccountId)
                .orElseThrow(() -> new RuntimeException("Loan account not found"));

        // SECURITY CHECK
        if (!account.getCustomerId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to EMI schedule");
        }

        List<EmiInstallment> emis =
                emiRepository.findByLoanAccountId(loanAccountId);

        return ApiResponse.builder()
                .success(true)
                .message("EMI schedule fetched successfully")
                .data(emis)
                .build();
    }

    // =========================
    // 2. PAY EMI
    // =========================
    @Override
    public ApiResponse<String> payEmi(UUID emiId) {

        User user = securityService.getCurrentUser();

        EmiInstallment emi = emiRepository.findById(emiId)
                .orElseThrow(() -> new RuntimeException("EMI not found"));

        LoanAccount account = loanAccountRepository.findById(emi.getLoanAccountId())
                .orElseThrow(() -> new RuntimeException("Loan account not found"));

        // SECURITY CHECK
        if (!account.getCustomerId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized payment attempt");
        }

        // VALIDATION
        if (emi.getStatus() == InstallmentStatus.PAID) {
            throw new RuntimeException("EMI already paid");
        }

        emi.setStatus(InstallmentStatus.PAID);

        emiRepository.save(emi);

        return ApiResponse.<String>builder()
                .success(true)
                .message("EMI payment successful")
                .data("PAID")
                .build();
    }

    // =========================
    // 3. REPAYMENT SUMMARY
    // =========================
    @Override
    public ApiResponse<?> getRepaymentSummary(UUID loanAccountId) {

        User user = securityService.getCurrentUser();

        LoanAccount account = loanAccountRepository.findById(loanAccountId)
                .orElseThrow(() -> new RuntimeException("Loan account not found"));

        // SECURITY CHECK
        if (!account.getCustomerId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        List<EmiInstallment> emis =
                emiRepository.findByLoanAccountId(loanAccountId);

        long total = emis.size();

        long paid = emis.stream()
                .filter(e -> e.getStatus() == InstallmentStatus.PAID)
                .count();

        long pending = emis.stream()
                .filter(e -> e.getStatus() == InstallmentStatus.PENDING)
                .count();

        long overdue = emis.stream()
                .filter(e -> e.getStatus() == InstallmentStatus.OVERDUE)
                .count();

        return ApiResponse.builder()
                .success(true)
                .message("Repayment summary fetched")
                .data(Map.of(
                        "totalInstallments", total,
                        "paidInstallments", paid,
                        "pendingInstallments", pending,
                        "overdueInstallments", overdue,
                        "loanStatus", account.getStatus()
                ))
                .build();
    }
}
