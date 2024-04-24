package com.example.clear_solutions.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponse {
    private List<Error> errors;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Error {
        private int status;
        private String detail;
        private int code;
        private Map<String, String> links;
    }
}