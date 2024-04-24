package com.example.clear_solutions.exception;

import com.example.clear_solutions.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        String url = attributes.getRequest().getRequestURL().toString();

        ErrorResponse.Error error = new ErrorResponse.Error();
        error.setStatus(400);
        return getErrorResponseEntity(e, url, error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeExceptionException(RuntimeException e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        String url = attributes.getRequest().getRequestURL().toString();

        ErrorResponse.Error error = new ErrorResponse.Error();
        error.setStatus(500);
        return getErrorResponseEntity(e, url, error);
    }

    private ResponseEntity<ErrorResponse> getErrorResponseEntity(Exception e, String url, ErrorResponse.Error error) {
        error.setDetail(e.getMessage());
        error.setCode(e.getStackTrace()[0].getLineNumber());
        error.setLinks(Map.of("about", url));

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(List.of(error));

        return ResponseEntity.status(400).body(errorResponse);
    }
}