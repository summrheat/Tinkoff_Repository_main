package ru.tinkoff.edu.java.jdbc;

import java.net.URI;
import java.sql.Timestamp;

public record Link(
        Integer id,
        URI url,
        Timestamp updateTimeAtScrap,
        Timestamp updateTime
) {
}
