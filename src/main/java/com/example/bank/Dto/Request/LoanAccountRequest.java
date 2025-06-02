package com.example.bank.Dto.Request;

import com.example.bank.Entity.LoanAccount;
import com.example.bank.Entity.TransferTransaction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanAccountRequest {
    BigDecimal loan_amount;
    int interest_rate;
    LoanAccount.TypeLoanAccount type;
    String status;
    Long bank_account;

}
