package com.task.test.exp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends RuntimeException {
    @ExceptionHandler({ServerBadRequestException.class})
    public ResponseEntity<?> handlerException(ServerBadRequestException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler({ItemNotFoundException.class})
    public ResponseEntity<?> handlerException(ItemNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler({MethodNotAllowedExc.class})
    public ResponseEntity<?> handlerException(MethodNotAllowedExc ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
