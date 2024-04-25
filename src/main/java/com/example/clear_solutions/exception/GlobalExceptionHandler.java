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
        return createErrorResponse(e, 400);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        return createErrorResponse(e, 500);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(Exception e, int status) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        String url = attributes.getRequest().getRequestURL().toString();

        ErrorResponse.Error error = new ErrorResponse.Error();
        error.setStatus(status);
        error.setDetail(e.getMessage());
        error.setCode(e.getStackTrace()[0].getLineNumber());
        error.setLinks(Map.of("about", url));

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(List.of(error));

        return ResponseEntity.status(status).body(errorResponse);
    }
}
