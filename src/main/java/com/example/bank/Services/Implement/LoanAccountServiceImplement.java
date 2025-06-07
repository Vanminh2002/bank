package com.example.bank.Services.Implement;

import com.example.bank.Dto.Request.LoanAccountRequest;
import com.example.bank.Dto.Request.TransferRequest;
import com.example.bank.Dto.Response.LoanAccountResponse;
import com.example.bank.Dto.Response.TransferResponse;
import com.example.bank.Entity.BankAccount;
import com.example.bank.Entity.TransferTransaction;
import com.example.bank.Exception.AppException;
import com.example.bank.Exception.ErrorCode;
import com.example.bank.Mapper.BankAccountMapper;
import com.example.bank.Mapper.TransferMapper;
import com.example.bank.Repository.BankAccountRepository;
import com.example.bank.Repository.TransferRepository;
import com.example.bank.Repository.UserRepository;
import com.example.bank.Services.LoanAccountService;
import com.example.bank.Services.TransferService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoanAccountServiceImplement implements LoanAccountService {

    @Override
    public LoanAccountResponse applyLoanAccount(LoanAccountRequest loanAccountRequest) {

        return null;
    }
}
