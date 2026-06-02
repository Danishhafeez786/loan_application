package com.loanapproval.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DashboardResponse {


    private String accountNumber;

    private Integer totalLoans;

    private Integer activeLoans;

    private Integer pendingLoans;

    private Integer approvedLoans;

    private Integer rejectedLoans;

    private BigDecimal totalBorrowed;

    private BigDecimal totalPaid;

    private BigDecimal outstandingBalance;

    private List<RecentLoanResponse> recentLoans;
}
