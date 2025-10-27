package ru.itmo.inf_sec.lab1.exception;

public class IncorrectAuthDataException extends RuntimeException {
    public IncorrectAuthDataException(String message) {
        super(message);
    }
}
