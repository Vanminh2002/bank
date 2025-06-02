package com.example.bank.Dto.Response;

import com.example.bank.Entity.LoanAccount;
import com.example.bank.Entity.TransferTransaction;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class LoanAccountResponse {
    Long id;
    // số tiền vay
    BigDecimal loan_amount;
    // lãi suất
    int interest_rate;
    // ngày vay ngay thời điểm tạo
    LocalDate start_date ;
    // ngày đáo hạn 2 năm
    LocalDate due_date;
    String type;
    String status;
}
