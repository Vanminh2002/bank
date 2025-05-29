package com.example.bank.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bank_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String account_number;
    @Enumerated(EnumType.STRING)
    type type;
    @Enumerated(EnumType.STRING)
    status status;
    @Column(precision = 19, scale = 4)
    BigDecimal balance;
    LocalDate created_at = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;


    public enum status{
        ACTIVE,
        UNACTIVE,
    }


    public enum type{
        SAVING,
        CHECKING,
    }
//    public String generalAccountNumber() {
//        // gán 4 số đầu bằng 1000
//        String prefix = "1000";
//        // Math.random tạo ra số double ngẫu nhiên trong khoản [0.0,1.0]
//        // (long) ép kiểu thành số nguyên long
//        // String.valueOf chuyển số nguyên thành chuỗi
//        String randomDigits = String.valueOf((long) (Math.random() * 1_0000_0000L));
//
//
//        // Long.parseLong(randomDigits) chuyển chuỗi số ngẫu nhiên thành số long
//        //  String.format("%08d" định dạng số sao cho đủ 8 chữ số, nếu thiếu cho thêm số 0 ở đầu
//        return prefix + String.format("%08d", Long.parseLong(randomDigits));
//    }
}
