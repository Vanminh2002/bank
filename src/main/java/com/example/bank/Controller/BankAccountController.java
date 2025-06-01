package com.example.bank.Controller;

import com.example.bank.Dto.Request.BankAccountRequest;
import com.example.bank.Dto.Response.ApiResponse;
import com.example.bank.Dto.Response.BankAccountResponse;
import com.example.bank.Entity.BankAccount;
import com.example.bank.Services.Implement.BankAccountServiceImplement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankAccountController {

    BankAccountServiceImplement bankAccountServiceImplement;


    @PostMapping
    ApiResponse<BankAccountResponse> createBankAccount(@RequestBody BankAccountRequest bankAccountRequest) {
        BankAccountResponse response = bankAccountServiceImplement.createBankAccount(bankAccountRequest);
        return ApiResponse.<BankAccountResponse>builder()
                .result(response)
                .message("Bank account created")
                .build();
    }

    @GetMapping
    ApiResponse<List<BankAccountResponse>> getAllBankAccounts() {
        List<BankAccountResponse> responses = bankAccountServiceImplement.findAllBankAccounts();
        return ApiResponse.<List<BankAccountResponse>>builder()
                .result(responses)
                .message("Bank accounts found")
                .build();
    }

    @GetMapping("/find-by/{id}")
    ApiResponse<BankAccountResponse> getBankAccountById(@PathVariable long id) {
        BankAccountResponse response = bankAccountServiceImplement.findBankAccountById(id);
        return ApiResponse.<BankAccountResponse>builder()
                .result(response)
                .message("Bank account found by id" + id)
                .build();
    }

    @GetMapping("/find-by-number/{number}")
    ApiResponse<BankAccountResponse> getAccountNumber(@PathVariable String number) {
        BankAccountResponse response = bankAccountServiceImplement.findBankAccountByBankAccountNumber(number);
        return ApiResponse.<BankAccountResponse>builder()
                .result(response)
                .message("Bank account number found by number: " + number)
                .build();
    }

    @PutMapping("/{number}/deposit")
    ApiResponse<BankAccountResponse> deposit(@PathVariable String number,
                                             @RequestParam BigDecimal amount) {
        try {
            BankAccountResponse updateBalance = bankAccountServiceImplement.depositBankAccount(number, amount);

            return ApiResponse.<BankAccountResponse>builder()
                    .result(updateBalance)
                    .message("Bank account deposited")
                    .build();
        } catch (Exception e) {
            return ApiResponse.<BankAccountResponse>builder()
                    .message("Deposit failed")
                    .build();
        }
    }

    @GetMapping("/find-by-user/{id}")
    ApiResponse<List<BankAccountResponse>> findBankAccountByUserId(@PathVariable Long id) {
        List<BankAccountResponse> response = bankAccountServiceImplement.findAccountUser(id);
        return ApiResponse.<List<BankAccountResponse>>builder()
                .result(response)
                .message("Bank account found by id" + id)
                .build();
    }
}


