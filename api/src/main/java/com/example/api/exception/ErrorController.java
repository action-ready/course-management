package com.example.api.exception;


import com.example.api.payload.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> constraintViolationException(ConstraintViolationException exception) {
        String message = exception.getMessage();

        int code = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder().message(message)
                        .code(code)
                        .build());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(400);
        errorResponse.setMessage(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}