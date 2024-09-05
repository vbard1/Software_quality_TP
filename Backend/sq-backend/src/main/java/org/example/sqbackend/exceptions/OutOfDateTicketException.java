package org.example.sqbackend.exceptions;

public class OutOfDateTicketException extends RuntimeException {
    public OutOfDateTicketException(String message) {
        super(message);
    }
}
