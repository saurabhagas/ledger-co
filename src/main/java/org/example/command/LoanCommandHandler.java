package org.example.command;

import org.example.exception.InvalidCommandFormatException;
import org.example.registry.CachingRegistryFactory;
import org.example.registry.LoanRegistry;

import java.util.Optional;

public class LoanCommandHandler implements CommandHandler {
    private final String commandName;
    private final LoanRegistry loanRegistry;

    public LoanCommandHandler(String commandName, CachingRegistryFactory registryFactory) {
        this.commandName = commandName;
        this.loanRegistry = registryFactory.getLoanRegistry();
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public void validate(String[] commandTokens) {
        if (commandTokens.length != 6) {
            throw new InvalidCommandFormatException("Invalid command format. Expected format is:" +
                commandName + " BANK_NAME BORROWER_NAME PRINCIPAL NO_OF_YEARS RATE_OF_INTEREST");
        }

        try {
            Integer.parseInt(commandTokens[3]);
            Integer.parseInt(commandTokens[4]);
            Integer.parseInt(commandTokens[5]);
        } catch (NumberFormatException e) {
            throw new InvalidCommandFormatException("Invalid data types in command. Expected types are:" +
                " PRINCIPAL(integer) NO_OF_YEARS(integer) RATE_OF_INTEREST(integer)");
        }
    }

    @Override
    public Optional<String> handle(String[] commandTokens) {
        String bankName = commandTokens[1];
        String customerName = commandTokens[2];
        long principal = Integer.parseInt(commandTokens[3]);
        int years = Integer.parseInt(commandTokens[4]);
        int interestRate = Integer.parseInt(commandTokens[4]);
        loanRegistry.registerLoan(bankName, customerName, principal, years, interestRate);
        return Optional.empty();
    }
}
