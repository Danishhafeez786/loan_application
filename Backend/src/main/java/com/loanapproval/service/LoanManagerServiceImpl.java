package com.loanapproval.service;

import com.loanapproval.domain.LoanApplication;
import com.loanapproval.domain.LoanStage;
import com.loanapproval.domain.LoanStatus;
import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.LoanApplicationResponse;
import com.loanapproval.repository.LoanApplicationRepository;
import com.loanapproval.transformer.LoanApplicationTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanManagerServiceImpl implements LoanManagerService {

    private final LoanApplicationRepository repository;
    private final LoanApplicationTransformer transformer;

    // -----------------------------
    // 1. GET PENDING LOANS
    // -----------------------------
    @Override
    public ApiResponse<List<LoanApplicationResponse>> getPendingLoans() {

        List<LoanApplication> loans = repository.findAll()
                .stream()
                .filter(this::isPendingForManager)
                .toList();

        List<LoanApplicationResponse> response =
                loans.stream()
                        .map(l -> transformer.toResponse(l, ""))
                        .collect(Collectors.toList());

        return ApiResponse.<List<LoanApplicationResponse>>builder()
                .success(true)
                .message("Pending manager approvals fetched")
                .data(response)
                .build();
    }

    // -----------------------------
    // 2. APPROVE LOAN
    // -----------------------------
    @Override
    public ApiResponse<LoanApplicationResponse> approveLoan(
            UUID id,
            BigDecimal approvedAmount,
            BigDecimal interestRate) {

        LoanApplication loan = getValidLoanForManager(id);

        validateApprovalData(approvedAmount, interestRate, loan);

        loan.setApprovedAmount(approvedAmount);
        loan.setApprovedInterestRate(interestRate);

        loan.setStatus(LoanStatus.APPROVED);
        loan.setStage(LoanStage.DISBURSEMENT);
        loan.setApprovedAt(LocalDateTime.now());

        repository.save(loan);

        return ApiResponse.<LoanApplicationResponse>builder()
                .success(true)
                .message("Loan approved successfully")
                .data(transformer.toResponse(loan, ""))
                .build();
    }

    // -----------------------------
    // 3. REJECT LOAN
    // -----------------------------
    @Override
    public ApiResponse<String> rejectLoan(UUID id, String reason) {

        LoanApplication loan = getValidLoanForManager(id);

        if (reason == null || reason.isBlank()) {
            throw new RuntimeException("Rejection reason is required");
        }

        loan.setStatus(LoanStatus.REJECTED);
        loan.setRejectionReason(reason);
        loan.setStage(null);

        repository.save(loan);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Loan rejected successfully")
                .data("REJECTED")
                .build();
    }

    // =====================================================
    // 🔐 PRIVATE VALIDATION METHODS (IMPORTANT)
    // =====================================================

    private LoanApplication getValidLoanForManager(UUID id) {

        LoanApplication loan = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Loan not found"));

        if (loan.getStage() != LoanStage.MANAGER_REVIEW) {
            throw new RuntimeException(
                    "Loan is not in manager review stage");
        }

        return loan;
    }

    private boolean isPendingForManager(LoanApplication loan) {

        return loan.getStage() == LoanStage.MANAGER_REVIEW
                && loan.getStatus() != LoanStatus.REJECTED
                && loan.getStatus() != LoanStatus.APPROVED;
    }

    private void validateApprovalData(
            BigDecimal approvedAmount,
            BigDecimal interestRate,
            LoanApplication loan) {

        if (approvedAmount == null || approvedAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid approved amount");
        }

        if (interestRate == null || interestRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid interest rate");
        }

        if (approvedAmount.compareTo(loan.getRequestedAmount()) > 0) {
            throw new RuntimeException(
                    "Approved amount cannot exceed requested amount");
        }
    }
}