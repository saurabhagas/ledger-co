package org.example.command;

import java.util.Optional;

public interface CommandHandler {
    /**
     * Get the command name handled by the given command handler
     * @return the name of the command
     */
    String getCommandName();

    /**
     * Validates the command
     * @throws RuntimeException if command format is invalid or other preconditions aren't met
     */
    void validate(String[] commandTokens) throws RuntimeException;

    /**
     * Handles a validated command
     * @return an optional result
     */
    Optional<String> handle(String[] commandTokens);
}
