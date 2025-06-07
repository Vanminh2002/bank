package com.example.bank.Services;


import com.example.bank.Dto.Request.LoanAccountRequest;
import com.example.bank.Dto.Response.LoanAccountResponse;

public interface LoanAccountService {
        LoanAccountResponse applyLoanAccount(LoanAccountRequest loanAccountRequest);

}
