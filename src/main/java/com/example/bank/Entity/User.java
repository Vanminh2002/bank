package com.example.bank.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true, nullable = false)

    String username;
    String password;
    String fullName;

    boolean deleted = false;
//    @OneToMany
    Set<String> roles;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<BankAccount> bankAccounts;


    public enum SetRoles {
        ADMIN,
        USER
    }
}
