package com.trongphuc.identity_service.exception;

import com.trongphuc.identity_service.dto.request.ApiRespond;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiRespond> handlingRuntimeException(RuntimeException exception) {
        ApiRespond apiRespond = new ApiRespond();

        apiRespond.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiRespond.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiRespond);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiRespond> handlingRAppException(AppException exception) {
        ApiRespond apiRespond = new ApiRespond();
        ErrorCode errorCode = exception.getErrorCode();

        apiRespond.setCode(errorCode.getCode());
        apiRespond.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiRespond);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiRespond> handlingValidation(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e) {
            
        }

        ApiRespond apiRespond = new ApiRespond();

        apiRespond.setCode(errorCode.getCode());
        apiRespond.setMessage(errorCode.getMessage());


        return ResponseEntity.badRequest().body(apiRespond);
    }
}
