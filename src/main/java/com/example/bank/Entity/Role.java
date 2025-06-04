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
@Table(name = "role")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Role {
    @Id

    String name;
    String description;

    @ManyToMany(fetch = FetchType.LAZY)
    Set<Permission> permissions;

}
