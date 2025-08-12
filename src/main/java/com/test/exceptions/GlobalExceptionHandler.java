package com.test.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.test.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleUsernameExists(UsernameAlreadyExistsException ex)
    {
       ApiResponse<Object> res = new ApiResponse<Object>(400, false, ex.getMessage(), null);
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidEmail(InvalidEmailException ex)
    {
       ApiResponse<Object> res = new ApiResponse<Object>(400, false, ex.getMessage(), null);
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(InvalidCredsException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidCreds(InvalidCredsException ex)
    {
      ApiResponse<Object> res = new ApiResponse<Object>(400, false, ex.getMessage(), null);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
}
