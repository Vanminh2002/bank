package com.example.bank.Dto.Response;

import com.example.bank.Entity.TransferTransaction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferResponse {
    String toAccount;
    String fromAccount;
    BigDecimal amount;
    String fromAccountBalance;
    String toAccountBalance;
    String description;
    TransferTransaction.TransactionType transactionType;
    String status;
    LocalDateTime timestamp;
}
