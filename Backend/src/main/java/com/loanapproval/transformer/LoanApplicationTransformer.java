package com.loanapproval.transformer;

import com.loanapproval.domain.LoanApplication;
import com.loanapproval.model.CreateLoanApplicationRequest;
import com.loanapproval.model.LoanApplicationResponse;
import org.springframework.stereotype.Component;

@Component
public class LoanApplicationTransformer {

    public LoanApplication toEntity(
            CreateLoanApplicationRequest request) {

        return LoanApplication.builder()
                .loanProductId(request.getLoanProductId())
                .requestedAmount(request.getRequestedAmount())
                .tenureMonths(request.getTenureMonths())
                .loanPurpose(request.getLoanPurpose())
                .build();
    }

    public LoanApplicationResponse toResponse(
            LoanApplication loan,
            String productName) {

        return LoanApplicationResponse.builder()
                .id(loan.getId())
                .loanProductId(loan.getLoanProductId())
                .loanProductName(productName)
                .requestedAmount(loan.getRequestedAmount())
                .tenureMonths(loan.getTenureMonths())
                .loanPurpose(loan.getLoanPurpose())
                .status(loan.getStatus())
                .stage(loan.getStage())
                .approvedAmount(loan.getApprovedAmount())
                .creditScore(loan.getCreditScore())
                .riskLevel(loan.getRiskLevel())
                .submittedAt(loan.getSubmittedAt())
                .build();
    }
}
