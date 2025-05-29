package com.example.bank.Dto.Response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankAccountResponse {
    Long id;
    String accountNumber;
    String type;
    String status;
    BigDecimal balance;
    LocalDate createdAt;
    Long user_id;
}
