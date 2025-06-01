package com.example.bank.Services;

import com.example.bank.Dto.Request.BankAccountRequest;
import com.example.bank.Dto.Request.TransferRequest;
import com.example.bank.Dto.Response.BankAccountResponse;
import com.example.bank.Dto.Response.TransferResponse;
import com.example.bank.Entity.BankAccount;

import java.math.BigDecimal;
import java.util.List;

public interface BankAccountService {
    BankAccountResponse createBankAccount(BankAccountRequest bankAccountRequest);


    List<BankAccountResponse> findAllBankAccounts();


    BankAccountResponse findBankAccountById(Long id);

    BankAccountResponse findBankAccountByBankAccountNumber(String bankAccountNumber);


    // nạp tiền
    BankAccountResponse depositBankAccount(String accountNumber, BigDecimal amount);
    // rút tiền
    // chuyển tiền
    TransferResponse transferBankAccount(TransferRequest request);

    List<BankAccountResponse> findAccountUser(Long id);
    // khóa tài khoản
    // mở khóa tài khoản

    void softDeleteBankAccount(Long id);
    void restoreBankAccount(Long id);
    BankAccountResponse getAllBankAccountsSoftDeleted();
}
