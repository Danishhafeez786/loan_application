package com.loanapproval.service;

import com.loanapproval.domain.*;
import com.loanapproval.model.ApiResponse;
import com.loanapproval.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LoanDisbursementServiceImpl implements LoanDisbursementService {

    private final LoanApplicationRepository loanRepository;
    private final LoanAccountRepository loanAccountRepository;
    private final EmiRepository emiRepository;

    // MAIN METHOD
    @Override
    public ApiResponse<String> disburseLoan(UUID loanId) {

        LoanApplication loan = getApprovedLoan(loanId);

        LoanAccount account = createLoanAccount(loan);

        updateLoanAsDisbursed(loan, account);

        generateEmiSchedule(account);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Loan disbursed successfully")
                .data(account.getAccountNumber())
                .build();
    }

    // ----------------------------
    // 1. VALIDATION METHOD
    // ----------------------------
    private LoanApplication getApprovedLoan(UUID loanId) {

        LoanApplication loan = loanRepository.findById(loanId)
                .orElseThrow(() ->
                        new RuntimeException("Loan not found"));

        if (loan.getStatus() != LoanStatus.APPROVED) {
            throw new RuntimeException("Loan is not approved yet");
        }

        return loan;
    }

    // ----------------------------
    // 2. CREATE LOAN ACCOUNT
    // ----------------------------
    private LoanAccount createLoanAccount(LoanApplication loan) {

        LoanAccount account = new LoanAccount();

        account.setId(UUID.randomUUID());
        account.setLoanApplicationId(loan.getId());
        account.setCustomerId(loan.getCustomerId());

        account.setAccountNumber(generateAccountNumber());

        account.setPrincipalAmount(loan.getApprovedAmount());
        account.setInterestRate(loan.getApprovedInterestRate());
        account.setTenureMonths(loan.getTenureMonths());

        account.setDisbursementDate(LocalDate.now());
        account.setStatus(LoanAccountStatus.ACTIVE);

        return loanAccountRepository.save(account);
    }

    // ----------------------------
    // 3. UPDATE LOAN STATUS
    // ----------------------------
    private void updateLoanAsDisbursed(
            LoanApplication loan,
            LoanAccount account) {

        loan.setStatus(LoanStatus.DISBURSED);
        loan.setStage(LoanStage.DISBURSED);
        loan.setApprovedAt(LocalDateTime.now());

        loanRepository.save(loan);
    }

    // ----------------------------
    // 4. EMI GENERATION ENGINE
    // ----------------------------
    private void generateEmiSchedule(LoanAccount account) {

        BigDecimal principal = account.getPrincipalAmount();
        BigDecimal annualRate = account.getInterestRate();
        int months = account.getTenureMonths();

        BigDecimal monthlyRate = annualRate
                .divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);

        BigDecimal basePrincipal = principal
                .divide(BigDecimal.valueOf(months), 2, RoundingMode.HALF_UP);

        for (int i = 1; i <= months; i++) {

            EmiInstallment emi = new EmiInstallment();

            emi.setId(UUID.randomUUID());
            emi.setLoanAccountId(account.getId());
            emi.setInstallmentNumber(i);

            BigDecimal interest =
                    principal.multiply(monthlyRate)
                            .setScale(2, RoundingMode.HALF_UP);

            emi.setInterestComponent(interest);
            emi.setPrincipalComponent(basePrincipal);

            emi.setAmount(basePrincipal.add(interest));

            emi.setDueDate(LocalDate.now().plusMonths(i));
            emi.setStatus(InstallmentStatus.PENDING);

            emiRepository.save(emi);
        }
    }

    // ----------------------------
    // 5. ACCOUNT NUMBER GENERATOR
    // ----------------------------
    private String generateAccountNumber() {
        return "LN-" + System.currentTimeMillis();
    }
}
