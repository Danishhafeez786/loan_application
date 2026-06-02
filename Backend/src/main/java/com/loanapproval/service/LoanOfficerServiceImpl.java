package com.loanapproval.service;

import com.loanapproval.domain.*;
import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.LoanApplicationResponse;
import com.loanapproval.repository.LoanApplicationRepository;
import com.loanapproval.transformer.LoanApplicationTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanOfficerServiceImpl implements LoanOfficerService {

    private final LoanApplicationRepository repository;
    private final LoanApplicationTransformer transformer;

    // 1️⃣ GET SUBMITTED LOANS (PROPER QUERY)
    @Override
    public ApiResponse<List<LoanApplicationResponse>> getSubmittedLoans() {

        List<LoanApplication> loans =
                repository.findAll()
                        .stream()
                        .filter(l ->
                                l.getStatus() == LoanStatus.SUBMITTED
                                        || l.getStage() == LoanStage.DOCUMENT_VERIFICATION
                        )
                        .toList();

        return ApiResponse.<List<LoanApplicationResponse>>builder()
                .success(true)
                .message("Submitted loans fetched successfully")
                .data(loans.stream()
                        .map(l -> transformer.toResponse(l, ""))
                        .toList())
                .build();
    }

    // 2️⃣ GET SINGLE LOAN
    @Override
    public ApiResponse<LoanApplicationResponse> getLoanById(UUID id) {

        LoanApplication loan = findLoanOrThrow(id);

        return ApiResponse.<LoanApplicationResponse>builder()
                .success(true)
                .message("Loan fetched successfully")
                .data(transformer.toResponse(loan, ""))
                .build();
    }

    // 3️⃣ VERIFY DOCUMENTS
    @Override
    public ApiResponse<String> verifyDocuments(UUID id) {

        LoanApplication loan = findLoanOrThrow(id);

        validateStage(loan, LoanStage.DOCUMENT_VERIFICATION);

        loan.setStage(LoanStage.CREDIT_ANALYSIS);
        loan.setRemarks("Documents verified by Loan Officer");

        repository.save(loan);

        return success("Documents verified and moved to credit analysis");
    }

    // 4️⃣ REQUEST CORRECTION
    @Override
    public ApiResponse<String> requestCorrection(UUID id, String message) {

        LoanApplication loan = findLoanOrThrow(id);

        validateStage(loan, LoanStage.DOCUMENT_VERIFICATION);

        loan.setStatus(LoanStatus.DOCUMENT_PENDING);
        loan.setStage(LoanStage.DOCUMENT_VERIFICATION);
        loan.setRemarks(message);

        repository.save(loan);

        return success("Correction requested from customer");
    }

    // 5️⃣ FORWARD TO CREDIT ANALYST
    @Override
    public ApiResponse<String> forwardToCredit(UUID id) {

        LoanApplication loan = findLoanOrThrow(id);

        if (loan.getStage() != LoanStage.DOCUMENT_VERIFICATION &&
                loan.getStage() != LoanStage.CREDIT_ANALYSIS) {

            throw new RuntimeException(
                    "Loan is not ready for credit analysis");
        }

        loan.setStage(LoanStage.CREDIT_ANALYSIS);
        loan.setStatus(LoanStatus.UNDER_REVIEW);

        repository.save(loan);

        return success("Loan forwarded to credit analyst");
    }

    // =======================
    // 🔧 PRIVATE HELPERS
    // =======================

    private LoanApplication findLoanOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Loan not found"));
    }

    private void validateStage(LoanApplication loan,
                               LoanStage expected) {

        if (loan.getStage() != expected) {
            throw new RuntimeException(
                    "Invalid loan stage. Expected: " + expected);
        }
    }

    private ApiResponse<String> success(String message) {
        return ApiResponse.<String>builder()
                .success(true)
                .message(message)
                .data("SUCCESS")
                .build();
    }
}
