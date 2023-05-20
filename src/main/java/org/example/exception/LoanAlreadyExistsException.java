package org.example.exception;

/**
 * Exception thrown when an attempt to give multiple loans to the same customer is made
 */
public class LoanAlreadyExistsException extends RuntimeException {
    public LoanAlreadyExistsException(String msg) {
        super(msg);
    }
}
