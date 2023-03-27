package ru.tinkoff.edu.java.parser;

// Класс для обработки ссылки со StackOverflow
public class StackOverflowParser {

    // Поле для хранения id вопроса
    private String questionId;

    // Конструктор, который принимает ссылку в виде строки и извлекает из нее id вопроса
    public StackOverflowParser(String link) {
        // Проверяем, что ссылка не пустая и начинается с https://stackoverflow.com/questions/
        if (link == null || link.isEmpty() || !link.startsWith("https://stackoverflow.com/questions/")) {
            throw new IllegalArgumentException("Неверный формат ссылки");
        }
        // Удаляем префикс https://stackoverflow.com/questions/ и разделяем оставшуюся часть по символу /
        String[] parts = link.substring(34).split("/");
        // Проверяем, что получили хотя бы одну часть: id вопроса
        if (parts.length < 1) {
            throw new IllegalArgumentException("Неверный формат ссылки");
        }
        // Присваиваем полю значение из первой части
        questionId = parts[0];
    }

    // Геттер для получения id вопроса
    public String getQuestionId() {
        return questionId;
    }
}
