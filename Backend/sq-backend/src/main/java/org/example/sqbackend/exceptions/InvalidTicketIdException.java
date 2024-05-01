package org.example.sqbackend.exceptions;

public class InvalidTicketIdException extends RuntimeException {
    public InvalidTicketIdException(String message) {
        super(message);
    }
}
