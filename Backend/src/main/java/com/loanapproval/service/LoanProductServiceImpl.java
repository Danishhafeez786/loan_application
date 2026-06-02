package com.loanapproval.service;

import com.loanapproval.domain.LoanProduct;
import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.CreateLoanProductRequest;
import com.loanapproval.model.LoanProductResponse;
import com.loanapproval.model.UpdateLoanProductRequest;
import com.loanapproval.repository.LoanProductRepository;
import com.loanapproval.transformer.LoanProductTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanProductServiceImpl implements LoanProductService {

    private final LoanProductRepository repository;
    private final LoanProductTransformer transformer;

    @Override
    public ApiResponse<LoanProductResponse> createLoanProduct(
            CreateLoanProductRequest request) {

        LoanProduct product =
                transformer.toEntity(request);

        product.setId(UUID.randomUUID());

        product.setCreatedAt(LocalDateTime.now());

        repository.save(product);

        return ApiResponse.<LoanProductResponse>builder()
                .success(true)
                .message("Loan product created successfully")
                .data(transformer.toResponse(product))
                .build();
    }

    @Override
    public ApiResponse<List<LoanProductResponse>>
    getActiveLoanProducts() {

        List<LoanProductResponse> response =
                repository.findByActiveTrue()
                        .stream()
                        .map(transformer::toResponse)
                        .toList();

        return ApiResponse.<List<LoanProductResponse>>builder()
                .success(true)
                .message("Loan products fetched successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<String> deactivateLoanProduct(UUID id) {

        LoanProduct product = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Loan product not found"));

        product.setActive(false);

        repository.save(product);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Loan product deactivated successfully")
                .data(null)
                .build();
    }

    @Override
    public ApiResponse<LoanProductResponse> updateLoanProduct(
            UUID id,
            UpdateLoanProductRequest request) {

        LoanProduct product = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Loan product not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setMinAmount(request.getMinAmount());
        product.setMaxAmount(request.getMaxAmount());
        product.setMinTenureMonths(request.getMinTenureMonths());
        product.setMaxTenureMonths(request.getMaxTenureMonths());
        product.setInterestRate(request.getInterestRate());
        product.setProcessingFee(request.getProcessingFee());
        product.setActive(request.getActive());

        repository.save(product);

        return ApiResponse.<LoanProductResponse>builder()
                .success(true)
                .message("Loan product updated successfully")
                .data(transformer.toResponse(product))
                .build();
    }

    @Override
    public ApiResponse<LoanProductResponse> getLoanProduct(UUID id) {

        LoanProduct product = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Loan product not found"));

        return ApiResponse.<LoanProductResponse>builder()
                .success(true)
                .message("Loan product fetched successfully")
                .data(transformer.toResponse(product))
                .build();
    }

    @Override
    public ApiResponse<List<LoanProductResponse>> getAllLoanProducts() {

        List<LoanProductResponse> products = repository.findAll()
                .stream()
                .map(transformer::toResponse)
                .toList();

        return ApiResponse.<List<LoanProductResponse>>builder()
                .success(true)
                .message("Loan products fetched successfully")
                .data(products)
                .build();
    }

}
