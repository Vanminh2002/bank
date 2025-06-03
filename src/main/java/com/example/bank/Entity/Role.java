package com.example.bank.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    String name;
    String description;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Permission> permissions;

}
