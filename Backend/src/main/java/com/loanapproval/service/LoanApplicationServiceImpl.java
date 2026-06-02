package com.loanapproval.service;

import com.loanapproval.domain.*;
import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.CreateLoanApplicationRequest;
import com.loanapproval.model.LoanApplicationResponse;
import com.loanapproval.repository.CustomerDocumentRepository;
import com.loanapproval.repository.CustomerProfileRepository;
import com.loanapproval.repository.LoanApplicationRepository;
import com.loanapproval.repository.LoanProductRepository;
import com.loanapproval.transformer.LoanApplicationTransformer;
import com.loanapproval.model.UpdateLoanApplicationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final CustomerProfileRepository profileRepository;
    private final CustomerDocumentRepository documentRepository;
    private final LoanProductRepository loanProductRepository;
    private final LoanApplicationTransformer transformer;
    private final LoanApplicationRepository repository;
    private final SecurityService securityService;

    @Override
    public ApiResponse<LoanApplicationResponse>
    createLoanApplication(
            CreateLoanApplicationRequest request) {

        User user = securityService.getCurrentUser();

        boolean isDraft = Boolean.TRUE.equals(request.getSaveAsDraft());

        LoanApplication loan =
                transformer.toEntity(request);

        loan.setId(UUID.randomUUID());
        loan.setCustomerId(user.getId());
        loan.setCreatedAt(LocalDateTime.now());

        // ==============================
        // 🟡 DRAFT FLOW (NO VALIDATION)
        // ==============================
        if (isDraft) {

            loan.setStatus(LoanStatus.DRAFT);
            loan.setStage(null);
            loan.setSubmittedAt(null);

            repository.save(loan);

            return ApiResponse.<LoanApplicationResponse>builder()
                    .success(true)
                    .message("Loan saved as draft")
                    .data(transformer.toResponse(loan, "Draft"))
                    .build();
        }

        // ==============================
        // 🟢 SUBMIT FLOW (FULL VALIDATION)
        // ==============================

        // 1. Profile Check
        CustomerProfile profile =
                profileRepository.findByUserId(user.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Please complete profile first"));

        // 2. Document Check
        List<CustomerDocument> documents =
                documentRepository.findByUserId(user.getId());

        if (documents.isEmpty()) {
            throw new RuntimeException(
                    "Please upload required documents");
        }

        // 3. Loan Product Check
        LoanProduct product =
                loanProductRepository.findById(
                                request.getLoanProductId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Loan product not found"));

        // 4. Amount Validation
        if (request.getRequestedAmount()
                .compareTo(product.getMinAmount()) < 0
                ||
                request.getRequestedAmount()
                        .compareTo(product.getMaxAmount()) > 0) {

            throw new RuntimeException(
                    "Requested amount outside allowed range");
        }

        // 5. Tenure Validation
        if (request.getTenureMonths()
                < product.getMinTenureMonths()
                ||
                request.getTenureMonths()
                        > product.getMaxTenureMonths()) {

            throw new RuntimeException(
                    "Invalid tenure");
        }

        // ==============================
        // FINAL SUBMIT STATE
        // ==============================

        loan.setStatus(LoanStatus.SUBMITTED);
        loan.setStage(LoanStage.DOCUMENT_VERIFICATION);
        loan.setSubmittedAt(LocalDateTime.now());

        repository.save(loan);

        return ApiResponse.<LoanApplicationResponse>builder()
                .success(true)
                .message("Loan application submitted successfully")
                .data(transformer.toResponse(loan, product.getName()))
                .build();
    }

    @Override
    public ApiResponse<List<LoanApplicationResponse>>
    getMyLoans() {

        User user = securityService.getCurrentUser();

        List<LoanApplicationResponse> loans =
                repository.findByCustomerId(user.getId())
                        .stream()
                        .map(loan -> {

                            LoanProduct product =
                                    loanProductRepository
                                            .findById(
                                                    loan.getLoanProductId())
                                            .orElse(null);

                            return transformer.toResponse(
                                    loan,
                                    product != null
                                            ? product.getName()
                                            : "Unknown");
                        })
                        .toList();

        return ApiResponse.<List<LoanApplicationResponse>>builder()
                .success(true)
                .message("Loans fetched successfully")
                .data(loans)
                .build();
    }

    @Override
    public ApiResponse<String> deleteLoan(UUID id) {

        User user = securityService.getCurrentUser();

        LoanApplication loan = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Loan not found"));

        // 🔒 Security check: only owner can delete
        if (!loan.getCustomerId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        // ❗ Business rule: only DRAFT or SUBMITTED can be deleted
        if (loan.getStatus() != LoanStatus.DRAFT &&
                loan.getStatus() != LoanStatus.SUBMITTED) {

            throw new RuntimeException(
                    "Loan cannot be deleted after processing started");
        }

        repository.delete(loan);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Loan deleted successfully")
                .data("Deleted")
                .build();
    }

    @Override
    public ApiResponse<LoanApplicationResponse> updateLoan(
            UUID id,
            UpdateLoanApplicationRequest request) {

        User user = securityService.getCurrentUser();

        LoanApplication loan = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Loan not found"));

        // only owner can update
        if (!loan.getCustomerId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        // only allow update if DRAFT or SUBMITTED
        if (!(loan.getStatus() == LoanStatus.DRAFT ||
                loan.getStatus() == LoanStatus.SUBMITTED)) {
            throw new RuntimeException("Loan cannot be updated now");
        }

        loan.setRequestedAmount(request.getRequestedAmount());
        loan.setTenureMonths(request.getTenureMonths());
        loan.setLoanPurpose(request.getLoanPurpose());

        repository.save(loan);

        LoanProduct product = loanProductRepository
                .findById(loan.getLoanProductId())
                .orElse(null);

        return ApiResponse.<LoanApplicationResponse>builder()
                .success(true)
                .message("Loan updated successfully")
                .data(transformer.toResponse(
                        loan,
                        product != null ? product.getName() : "Unknown"))
                .build();
    }

    @Override
    public ApiResponse<LoanApplicationResponse> getLoanById(UUID id) {

        User user = securityService.getCurrentUser();

        LoanApplication loan = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Loan not found"));

        // security check (customer can only see own loan)
        if (!loan.getCustomerId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        LoanProduct product = loanProductRepository
                .findById(loan.getLoanProductId())
                .orElse(null);

        return ApiResponse.<LoanApplicationResponse>builder()
                .success(true)
                .message("Loan fetched successfully")
                .data(transformer.toResponse(
                        loan,
                        product != null ? product.getName() : "Unknown"))
                .build();
    }

    @Override
    public ApiResponse<String> submitDraft(UUID id) {

        User user = securityService.getCurrentUser();

        LoanApplication loan = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (!loan.getCustomerId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        if (loan.getStatus() != LoanStatus.DRAFT) {
            throw new RuntimeException("Only draft loans can be submitted");
        }

        loan.setStatus(LoanStatus.SUBMITTED);
        loan.setStage(LoanStage.DOCUMENT_VERIFICATION);
        loan.setSubmittedAt(LocalDateTime.now());

        repository.save(loan);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Loan submitted successfully")
                .data("SUCCESS")
                .build();
    }
}
