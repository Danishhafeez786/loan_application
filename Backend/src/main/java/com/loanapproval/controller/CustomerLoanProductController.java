package com.loanapproval.controller;

import com.loanapproval.model.ApiResponse;
import com.loanapproval.model.LoanProductResponse;
import com.loanapproval.service.LoanProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer/loan-products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerLoanProductController {

    private final LoanProductService service;

    @GetMapping
    public ApiResponse<List<LoanProductResponse>> getActiveProducts() {

        return service.getActiveLoanProducts();
    }
}
