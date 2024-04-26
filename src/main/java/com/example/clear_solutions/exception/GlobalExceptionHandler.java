package com.example.clear_solutions.exception;

import com.example.clear_solutions.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;

/**
 * GlobalExceptionHandler is a controller advice that handles exceptions globally across the whole application.
 * It provides centralized exception handling across all @RequestMapping methods.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IllegalArgumentExceptions.
     *
     * @param e the IllegalArgumentException to be handled
     * @return a ResponseEntity containing an ErrorResponse with details about the exception
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return createErrorResponse(e, 400);
    }

    /**
     * Handles RuntimeExceptions.
     *
     * @param e the RuntimeException to be handled
     * @return a ResponseEntity containing an ErrorResponse with details about the exception
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        return createErrorResponse(e, 500);
    }

    /**
     * Creates an ErrorResponse for the specified Exception and status.
     *
     * @param e the Exception to be included in the ErrorResponse
     * @param status the status to be included in the ErrorResponse
     * @return a ResponseEntity containing the ErrorResponse
     */
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