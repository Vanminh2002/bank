package com.example.bank.Services;

import com.example.bank.Dto.Request.BankAccountRequest;
import com.example.bank.Dto.Request.TransferRequest;
import com.example.bank.Dto.Response.BankAccountResponse;
import com.example.bank.Dto.Response.TransferResponse;

import java.math.BigDecimal;
import java.util.List;

public interface TransferService {



    // nạp tiền
//    BankAccountResponse depositBankAccount(String accountNumber, BigDecimal amount);
    // rút tiền
    // chuyển tiền
    TransferResponse transferBankAccount(TransferRequest request);


    // khóa tài khoản
    // mở khóa tài khoản


}
