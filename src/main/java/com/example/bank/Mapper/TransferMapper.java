package com.example.bank.Mapper;

import com.example.bank.Dto.Request.BankAccountRequest;
import com.example.bank.Dto.Request.TransferRequest;
import com.example.bank.Dto.Response.BankAccountResponse;
import com.example.bank.Dto.Response.TransferResponse;
import com.example.bank.Entity.BankAccount;
import com.example.bank.Entity.TransferTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface TransferMapper {

    TransferTransaction toRequest(TransferRequest request);


    @Mapping(source = "formAccount" , target = "fromAccount")
    TransferResponse toResponse(TransferTransaction transaction);


    TransferTransaction createDepositTransaction(String toAccount, BigDecimal amount);
    TransferTransaction createWithdrawTransaction(String fromAccount, BigDecimal amount);
    TransferTransaction createTransferTransaction(String fromAccount, String toAccount, BigDecimal amount);



    public void updateBankAccount(@MappingTarget BankAccount account, BankAccountRequest bankAccountRequest);
}
