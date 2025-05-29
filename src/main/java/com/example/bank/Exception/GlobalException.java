package com.example.bank.Exception;

import com.example.bank.Dto.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> runtimeExceptionHandler(RuntimeException e) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(e.getMessage());
        apiResponse.setCode(999);
        return ResponseEntity.badRequest().body(apiResponse);
    }
    @ExceptionHandler( value = MethodArgumentNotValidException.class)
    ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {

        return ResponseEntity.badRequest().body(e.getFieldError().getDefaultMessage());
    }


    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> appException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(e.getMessage());
        apiResponse.setCode(errorCode.getCode());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
