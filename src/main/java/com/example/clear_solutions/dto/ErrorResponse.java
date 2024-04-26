package com.example.clear_solutions.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * ErrorResponse is a DTO (Data Transfer Object) that represents an error response.
 * It contains a list of Error objects.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponse {
    /**
     * A list of Error objects.
     */
    private List<Error> errors;

    /**
     * Error is a static inner class that represents an individual error.
     * It contains details about the error such as status, detail message, error code, and links.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Error {
        /**
         * The HTTP status code of the error.
         */
        private int status;

        /**
         * The detail message of the error.
         */
        private String detail;

        /**
         * The application-specific error code.
         */
        private int code;

        /**
         * A map of links related to the error.
         */
        private Map<String, String> links;
    }
}