package ru.liga.exception;

public class IncorrectOrderStatusException extends RuntimeException {
    public IncorrectOrderStatusException(String message) {
        super(message);
    }
}
