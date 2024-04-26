package com.example.clear_solutions.exception;

import com.example.clear_solutions.dto.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * GlobalExceptionHandlerTest is a test class for GlobalExceptionHandler.
 * It uses Spring's MockHttpServletRequest to simulate HTTP requests.
 * It also uses Mockito to inject mocks.
 */
@SpringBootTest
public class GlobalExceptionHandlerTest {

    /**
     * The GlobalExceptionHandler instance to be tested.
     * This instance is automatically injected with mocks by Mockito.
     */
    @InjectMocks
    GlobalExceptionHandler globalExceptionHandler;

    /**
     * Sets up the tests.
     * This method is run before each test.
     * It opens the mocks and sets up the RequestContextHolder.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    /**
     * Tests that handling an IllegalArgumentException returns a 400 error response.
     */
    @Test
    @DisplayName("IllegalArgumentException returns a 400 error response")
    public void handleIllegalArgumentExceptionReturnsBadRequest() {
        IllegalArgumentException exception = new IllegalArgumentException("Test exception");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleIllegalArgumentException(exception);

        assertEquals(400, response.getStatusCodeValue());
    }

    /**
     * Tests that handling a RuntimeException returns a 500 error response.
     */
    @Test
    @DisplayName("RuntimeException returns a 500 error response")
    public void handleRuntimeExceptionReturnsInternalServerError() {
        RuntimeException exception = new RuntimeException("Test exception");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleRuntimeException(exception);

        assertEquals(500, response.getStatusCodeValue());
    }
}