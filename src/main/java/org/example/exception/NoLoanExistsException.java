package org.example.exception;

/**
 * Exception thrown when a payment is attempted without a loan
 */
public class NoLoanExistsException extends RuntimeException {
    public NoLoanExistsException(String msg) {
        super(msg);
    }
}
