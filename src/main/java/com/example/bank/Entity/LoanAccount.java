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
@Table(name = "loan_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    // số tiền vay
    BigDecimal loan_amount;
    // lãi suất
    int interest_rate;
    // ngày vay ngay thời điểm tạo
    LocalDate start_date = LocalDate.now();
    // ngày đáo hạn 2 năm
    LocalDate due_date = start_date.plusYears(2);
    @Enumerated(EnumType.STRING)
    TypeLoanAccount type;
    String status;
    @ManyToOne
    BankAccount bank_account;
    @ManyToOne
    RepaymentAccount repayment_account;


//    public void setInterest_rate(int interest_rate) {
//        if (type==TypeLoanAccount.SUPPORT){
//            this.interest_rate = 5;
//        } else if (type==TypeLoanAccount.PERSONAL) {
//            this.interest_rate = 8;
//        }else if (type==TypeLoanAccount.BUSINESS) {
//            this.interest_rate = 10;
//        }
//        this.interest_rate = 3;
//    }

    public void setType(TypeLoanAccount type) {
        this.type = type;
        this.interest_rate = type.getInteres();
    }

    public enum TypeLoanAccount {
        PERSONAL(3),
        BUSINESS(5),
        SUPPORT(10);
        private final int interes;

        TypeLoanAccount(int interes) {
            this.interes = interes;
        }

        public int getInteres() {
            return interes;
        }
    }
}
