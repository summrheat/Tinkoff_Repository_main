package ru.tinkoff.edu.java.parsers;

public sealed interface Parse permits GitHubParser, StackOverFlowParser {
    public String parse(String URL);
}
