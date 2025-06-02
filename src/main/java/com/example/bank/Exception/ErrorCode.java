package com.example.bank.Exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXISTED(1000, "User already existed"),
    USER_NOT_FOUND(1001, "User Not Found"),
    BANK_NUMBER_NOT_FOUND(1002, "Bank Number Not Found"),
    BANK_ACCOUNT_NOT_FOUND(1003, "Bank Account Not Found"),
    USER_DO_NOT_HAVE_ACCOUNT(1004, "User Do Not Have Account"),
    AUTHENTICATION_FAILED(1005, "Authentication Failed"),
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;
}
