package ru.itmo.inf_sec.lab1.exception;

public class PasswordsNotEqualException extends RuntimeException {
    public PasswordsNotEqualException(String message) {
        super(message);
    }
}
