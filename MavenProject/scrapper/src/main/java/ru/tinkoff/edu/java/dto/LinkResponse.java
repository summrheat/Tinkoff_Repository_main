package ru.tinkoff.edu.java.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.net.URI;

@Validated
public record LinkResponse(
        @NotNull
        Integer id,

        @NotNull
        URI url
        ) {
}
