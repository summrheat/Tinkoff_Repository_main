package ru.tinkoff.edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.net.URI;

@Validated
public record LinkUpdaterRequest(
        @NotNull
        @JsonProperty("id")
        Integer id,

        @NotNull
        @JsonProperty("url")
        URI url,

        @NotNull
        @JsonProperty("description")
        String description,

        @NotNull
        @JsonProperty("tgChatIds")
        Integer[] tgChatIds
        ) implements Serializable {
}
