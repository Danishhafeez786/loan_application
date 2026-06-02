package com.loanapproval.transformer;

import com.loanapproval.domain.LoanProduct;
import com.loanapproval.model.CreateLoanProductRequest;
import com.loanapproval.model.LoanProductResponse;
import org.springframework.stereotype.Component;

@Component
public class LoanProductTransformer {

    public LoanProduct toEntity(
            CreateLoanProductRequest request) {

        return LoanProduct.builder()
                .name(request.getName())
                .description(request.getDescription())
                .loanType(request.getLoanType())
                .minAmount(request.getMinAmount())
                .maxAmount(request.getMaxAmount())
                .minTenureMonths(request.getMinTenureMonths())
                .maxTenureMonths(request.getMaxTenureMonths())
                .interestRate(request.getInterestRate())
                .processingFee(request.getProcessingFee())
                .active(true)
                .build();
    }

    public LoanProductResponse toResponse(
            LoanProduct product) {

        return LoanProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .loanType(product.getLoanType())
                .minAmount(product.getMinAmount())
                .maxAmount(product.getMaxAmount())
                .minTenureMonths(product.getMinTenureMonths())
                .maxTenureMonths(product.getMaxTenureMonths())
                .interestRate(product.getInterestRate())
                .processingFee(product.getProcessingFee())
                .build();
    }
}
