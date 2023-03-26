package ru.tinkoff.edu.java.parser;

// Класс для обработки ссылки с GitHub
public class GitHubParser {

    private String login;
    private String repo;

    // Конструктор, который принимает ссылку в виде строки и разбивает ее на части
    public GitHubParser(String link) {
        // Проверяем, что ссылка не пустая и начинается с https://github.com/
        if (link == null || link.isEmpty() || !link.startsWith("https://github.com/")) {
            throw new IllegalArgumentException("Неверный формат ссылки");
        }
        // Удаляем префикс https://github.com/ и разделяем оставшуюся часть по символу /
        String[] parts = link.substring(19).split("/");
        // Проверяем, что получили две части: логин и название репозитория
        if (parts.length != 2) {
            throw new IllegalArgumentException("Неверный формат ссылки");
        }
        // Присваиваем полям значения из частей
        login = parts[0];
        repo = parts[1];
    }


    public String getLogin() {
        return login;
    }

    public String getRepo() {
        return repo;
    }
}