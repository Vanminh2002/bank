package com.example.bank.Dto.Request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    @Size(min = 5, message = "USERNAME_INVALID")
    String username;

    @Size(min = 5, message = "PASSWORD_INVALID")

    String password;
    String fullName;
}
