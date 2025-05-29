package com.example.bank.Controller;

import com.example.bank.Dto.Request.BankAccountRequest;
import com.example.bank.Dto.Request.TransferRequest;
import com.example.bank.Dto.Response.ApiResponse;
import com.example.bank.Dto.Response.BankAccountResponse;
import com.example.bank.Dto.Response.TransferResponse;
import com.example.bank.Services.Implement.BankAccountServiceImplement;
import com.example.bank.Services.Implement.TransferServiceImplement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransferController {

    TransferServiceImplement transferServiceImplement;

    @PostMapping
    ApiResponse<TransferResponse> transfer(@RequestBody TransferRequest request) {
        TransferResponse response = transferServiceImplement.transferBankAccount(request);
        return ApiResponse.<TransferResponse>builder()
                .result(response)
                .message("Transfer successful")
                .build();
    }
}


