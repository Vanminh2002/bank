package com.example.bank.Mapper;

import com.example.bank.Dto.Request.BankAccountRequest;
import com.example.bank.Dto.Response.BankAccountResponse;
import com.example.bank.Entity.BankAccount;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    BankAccount toRequest(BankAccountRequest bankAccountRequest);

    @Mapping(target = "accountNumber",source = "account_number")
    @Mapping(target = "createdAt",source = "created_at")
    @Mapping(target = "user_id",source = "user.id")
    BankAccountResponse toResponse(BankAccount bankAccount);


    public void updateBankAccount(@MappingTarget BankAccount account, BankAccountRequest bankAccountRequest);
}
