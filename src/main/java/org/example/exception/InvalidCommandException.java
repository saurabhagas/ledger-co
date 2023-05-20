package org.example.exception;

/**
 * Exception thrown when the command is invalid
 */
public class InvalidCommandException extends RuntimeException {
    public InvalidCommandException(String msg) {
        super(msg);
    }
}
