package org.example.command;

import org.example.exception.InvalidCommandFormatException;

import java.util.Optional;

public interface CommandHandler {
    /**
     * Validates the command
     * @throws InvalidCommandFormatException if command format is invalid
     */
    void validate(String[] commandTokens) throws InvalidCommandFormatException;

    /**
     * Handles a validated command
     * @return an optional result
     * @throws RuntimeException if preconditions or invariant-checks fail
     */
    Optional<String> handle(String[] commandTokens) throws RuntimeException;
}
