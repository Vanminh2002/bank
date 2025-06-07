package com.example.bank.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "loan_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    // số tiền vay
    BigDecimal loanAmount;
    // lãi suất
    int interestRate;
    // ngày vay ngay thời điểm tạo
    LocalDate startDate;
    // ngày đáo hạn 2 năm
    LocalDate dueDate;
    @Enumerated(EnumType.STRING)
    TypeLoanAccount type;
    String status;


    @ManyToOne
    BankAccount bankAccount;
    @OneToMany(mappedBy = "loanAccount", cascade = CascadeType.ALL)
    List<RepaymentAccount> repaymentAccount;


    @PrePersist
    protected void prePersist() {
        // nếu ngày bắt đầu bằng null thì gán bằng ngày hôm nay
        if (startDate == null) startDate = LocalDate.now();
        // nếu ngày đáo hạn bằng null thì gán này đáo hạn == 2 năm sau ngày bắt đầu
        if (dueDate == null) dueDate = startDate.plusYears(2);


    }

    public void setType(TypeLoanAccount type) {
        this.type = type;
        this.interestRate = type.getInterest();
    }

    @Getter
    public enum TypeLoanAccount {
        PERSONAL(3),
        BUSINESS(5),
        SUPPORT(10);
        private final int interest;

        TypeLoanAccount(int interest) {
            this.interest = interest;
        }
    }
}
