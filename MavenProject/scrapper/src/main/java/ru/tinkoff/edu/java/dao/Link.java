package ru.tinkoff.edu.java.dao;

import java.net.URI;

public record Link(
        Integer id,
        URI url
) {
}
