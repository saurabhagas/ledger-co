package org.example.exception;

/**
 * Exception thrown when the format of a command is not as per specification
 */
public class InvalidCommandFormatException extends RuntimeException {
    public InvalidCommandFormatException(String msg) {
        super(msg);
    }
}
