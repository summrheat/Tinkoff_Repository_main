package ru.tinkoff.edu.java.dto;


import java.time.OffsetDateTime;

public record GitHubResponse(
        OffsetDateTime pushedAt,

        OffsetDateTime updatedAt,

        String fullName
) {
}