package com.example.clear_solutions.exception;

import com.example.clear_solutions.dto.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    @InjectMocks
    GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    @DisplayName("IllegalArgumentException returns a 400 error response")
    public void handleIllegalArgumentExceptionReturnsBadRequest() {
        IllegalArgumentException exception = new IllegalArgumentException("Test exception");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleIllegalArgumentException(exception);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("RuntimeException returns a 500 error response")
    public void handleRuntimeExceptionReturnsInternalServerError() {
        RuntimeException exception = new RuntimeException("Test exception");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleRuntimeException(exception);

        assertEquals(500, response.getStatusCodeValue());
    }
}