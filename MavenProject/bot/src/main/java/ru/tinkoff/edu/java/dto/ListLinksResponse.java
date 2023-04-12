package ru.tinkoff.edu.java.dto;

import java.util.List;

public record ListLinksResponse(
        Integer size,
        List<LinkResponse> links
) {
}
