package com.example.bank.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String formAccount;
    String toAccount;
    BigDecimal amount;
    LocalDateTime timestamp = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    Status status = Status.PENDING;
    String description;
    BigDecimal fromAccountBalance;
    BigDecimal toAccountBalance;
    @Enumerated(EnumType.STRING)
    TransactionType transactionType;

    public enum Status {
        SUCCESS,
        FAILED,
        PENDING,
    }

    public enum TransactionType {
        TRANSFER,
        DEPOSIT,
        WITHDRAW,
    }


}
