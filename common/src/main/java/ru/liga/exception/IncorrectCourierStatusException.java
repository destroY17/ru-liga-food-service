package ru.liga.exception;

public class IncorrectCourierStatusException extends RuntimeException {
    public IncorrectCourierStatusException(String message) {
        super(message);
    }
}
