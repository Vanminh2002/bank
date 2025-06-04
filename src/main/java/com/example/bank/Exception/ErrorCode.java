package com.example.bank.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_EXISTED(1000, "User already existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1001, "User Not Found", HttpStatus.NOT_FOUND),
    BANK_NUMBER_NOT_FOUND(1002, "Bank Number Not Found", HttpStatus.NOT_FOUND),
    BANK_ACCOUNT_NOT_FOUND(1003, "Bank Account Not Found", HttpStatus.NOT_FOUND),
    USER_DO_NOT_HAVE_ACCOUNT(1004, "User Do Not Have Account", HttpStatus.NOT_FOUND),
    AUTHENTICATION_FAILED(1005, "Authentication Failed", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1006, "Bạn không có quyền", HttpStatus.FORBIDDEN),
    USERNAME_INVALID(1007, "Username must be at least 5 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1008, "Password must be 5 to 20 characters", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(1009, "Permission already existed", HttpStatus.CONFLICT),
    PERMISSION_NOT_FOUND(10010, "Permission Not Found", HttpStatus.NOT_FOUND),
    ROLE_EXISTED(10011, "Bạn đã có quyền này", HttpStatus.CONFLICT),
    ROLE_NOT_FOUND(10012, "Role Not Found", HttpStatus.NOT_FOUND),
    ;

    ErrorCode(int code, String message,HttpStatus httpStatus) {
        this.status = httpStatus;
        this.code = code;
        this.message = message;
    }
    private HttpStatus status;
    private int code;
    private String message;
}
