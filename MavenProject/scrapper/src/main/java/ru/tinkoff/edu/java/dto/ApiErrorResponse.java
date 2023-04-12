package ru.tinkoff.edu.java.dto;

import org.springframework.validation.annotation.Validated;

@Validated
public record ApiErrorResponse(
        String description,
        String code,
        String exceptionName,
        String exceptionMessage,
        String[] stacktrace
) {
}
