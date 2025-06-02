package com.example.bank.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "repayment_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RepaymentAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    // ngày đáo hạn
    LocalDate due_date;
    // số tiền gốc
    BigDecimal principal_amount;
    // số tiền lãi
    BigDecimal interest_amount;
    // tổng tiền phải trả
    BigDecimal total_amount;
    String status;

    @ManyToOne
    LoanAccount loanAccountList;


    public void calculateInterestAmount() {
        if (principal_amount != null && loanAccountList != null && loanAccountList.getInterest_rate() > 0) {
            BigDecimal rate = BigDecimal.valueOf(loanAccountList.getInterest_rate()).divide(BigDecimal.valueOf(100));
            this.interest_amount = principal_amount.multiply(rate);
        } else
            this.interest_amount = BigDecimal.ZERO;
    }

    public void calculateTotalAmount() {
        if (principal_amount != null && loanAccountList != null && loanAccountList.getInterest_rate() > 0) {
//            BigDecimal rate = BigDecimal.valueOf(loanAccountList.getInterest_rate()).divide(BigDecimal.valueOf(100));
            this.total_amount = principal_amount.add(interest_amount);
        } else
            this.total_amount = principal_amount != null ? principal_amount : BigDecimal.ZERO;
    }

    public void setInterest_amount(BigDecimal interest_amount) {
        calculateInterestAmount();
    }

    public void setTotal_amount(BigDecimal total_amount) {
        calculateTotalAmount();
    }
}
