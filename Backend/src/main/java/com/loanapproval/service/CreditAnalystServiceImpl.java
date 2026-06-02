package com.loanapproval.service;

import com.loanapproval.domain.*;
import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.LoanApplicationResponse;
import com.loanapproval.repository.CustomerProfileRepository;
import com.loanapproval.repository.LoanApplicationRepository;
import com.loanapproval.repository.LoanProductRepository;
import com.loanapproval.service.CreditAnalystService;
import com.loanapproval.transformer.LoanApplicationTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreditAnalystServiceImpl implements CreditAnalystService {

    private final LoanApplicationRepository repository;
    private final CustomerProfileRepository profileRepository;
    private final LoanProductRepository loanProductRepository;
    private final LoanApplicationTransformer transformer;

    // ---------------------------------------------------
    // 1. GET LOANS UNDER CREDIT ANALYSIS
    // ---------------------------------------------------
    @Override
    public ApiResponse<List<LoanApplicationResponse>> getUnderReviewLoans() {

        List<LoanApplication> loans = repository.findAll()
                .stream()
                .filter(l -> l.getStage() == LoanStage.CREDIT_ANALYSIS)
                .toList();

        List<LoanApplicationResponse> response = loans.stream()
                .map(l -> {
                    LoanProduct product = loanProductRepository
                            .findById(l.getLoanProductId())
                            .orElse(null);

                    return transformer.toResponse(
                            l,
                            product != null ? product.getName() : "UNKNOWN"
                    );
                })
                .toList();

        return ApiResponse.<List<LoanApplicationResponse>>builder()
                .success(true)
                .message("Loans under credit analysis fetched successfully")
                .data(response)
                .build();
    }

    // ---------------------------------------------------
    // 2. ANALYZE LOAN (CORE BUSINESS LOGIC)
    // ---------------------------------------------------
    @Override
    public ApiResponse<LoanApplicationResponse> analyzeLoan(UUID id) {

        LoanApplication loan = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getStage() != LoanStage.CREDIT_ANALYSIS) {
            throw new RuntimeException("Loan is not in credit analysis stage");
        }

        CustomerProfile profile = profileRepository.findByUserId(loan.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer profile not found"));

        LoanProduct product = loanProductRepository.findById(loan.getLoanProductId())
                .orElseThrow(() -> new RuntimeException("Loan product not found"));

        // -----------------------------
        // 🧠 CREDIT SCORE ENGINE
        // -----------------------------
        int income = profile.getMonthlyIncome().intValue();
        int requested = loan.getRequestedAmount().intValue();

        int score = calculateCreditScore(income, requested);

        RiskLevel risk = calculateRisk(score);

        // -----------------------------
        // UPDATE LOAN
        // -----------------------------
        loan.setCreditScore(score);
        loan.setRiskLevel(risk);
        loan.setStage(LoanStage.MANAGER_REVIEW);

        repository.save(loan);

        return ApiResponse.<LoanApplicationResponse>builder()
                .success(true)
                .message("Credit analysis completed successfully")
                .data(transformer.toResponse(loan, product.getName()))
                .build();
    }

    // ---------------------------------------------------
    // 3. FORWARD TO MANAGER
    // ---------------------------------------------------
    @Override
    public ApiResponse<String> forwardToManager(UUID id) {

        LoanApplication loan = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getStage() != LoanStage.MANAGER_REVIEW) {
            throw new RuntimeException("Loan not ready for manager review");
        }

        loan.setStage(LoanStage.MANAGER_REVIEW);

        repository.save(loan);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Loan forwarded to manager successfully")
                .data("FORWARDED")
                .build();
    }

    // ---------------------------------------------------
    // 🧠 PRIVATE BUSINESS LOGIC METHODS
    // ---------------------------------------------------

    private int calculateCreditScore(int income, int requestedAmount) {

        int score = 0;

        // Income strength
        if (income >= 300000) {
            score += 350;
        } else if (income >= 150000) {
            score += 250;
        } else {
            score += 150;
        }

        // Loan-to-income ratio
        double ratio = (double) requestedAmount / income;

        if (ratio <= 1.5) {
            score += 350;
        } else if (ratio <= 3) {
            score += 250;
        } else if (ratio <= 5) {
            score += 150;
        } else {
            score += 50;
        }

        // Stability buffer (base trust score)
        score += 150;

        // Cap score
        return Math.min(score, 850);
    }

    private RiskLevel calculateRisk(int score) {

        if (score >= 700) {
            return RiskLevel.LOW;
        } else if (score >= 500) {
            return RiskLevel.MEDIUM;
        } else {
            return RiskLevel.HIGH;
        }
    }
}
