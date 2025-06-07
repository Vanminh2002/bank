package com.example.bank.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "repayment_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RepaymentAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    // ngày đáo hạn
    LocalDate dueDate;
    // số tiền gốc
    BigDecimal principalAmount;
    // số tiền lãi
    BigDecimal interestAmount;
    // tổng tiền phải trả
    BigDecimal totalAmount;
    String status;

    @ManyToOne
    @JoinColumn(name = "loan_account_id")
    LoanAccount loanAccount;


    public void calculateInterestAmount() {
        if (principalAmount != null && loanAccount != null && loanAccount.getInterestRate() > 0) {
            BigDecimal rate = BigDecimal.valueOf(loanAccount.getInterestRate()).divide(BigDecimal.valueOf(100));
            this.interestAmount = principalAmount.multiply(rate);
        } else
            this.interestAmount = BigDecimal.ZERO;
    }

    public void calculateTotalAmount() {
        if (principalAmount != null && loanAccount != null && loanAccount.getInterestRate() > 0) {
//            BigDecimal rate = BigDecimal.valueOf(loanAccountList.getInterest_rate()).divide(BigDecimal.valueOf(100));
            this.totalAmount = principalAmount.add(interestAmount);
        } else
            this.totalAmount = principalAmount != null ? principalAmount : BigDecimal.ZERO;
    }
}
