package org.example.command;

import org.example.exception.InvalidCommandFormatException;
import org.example.exception.NoLoanExistsException;
import org.example.model.CustomerIdentity;
import org.example.registry.PaymentRegistry;
import org.example.registry.CachingRegistryFactory;
import org.example.registry.LoanRegistry;

import java.util.Optional;

public class PaymentCommandHandler implements CommandHandler {
    private final String commandName;
    private final LoanRegistry loanRegistry;
    private final PaymentRegistry paymentRegistry;

    public PaymentCommandHandler(String commandName, CachingRegistryFactory registryFactory) {
        this.commandName = commandName;
        this.loanRegistry = registryFactory.getLoanRegistry();
        this.paymentRegistry = registryFactory.getPaymentRegistry();
    }

    @Override
    public void validate(String[] commandTokens) {
        if (commandTokens.length != 5) {
            throw new InvalidCommandFormatException("Invalid command format. Expected format is:" +
                commandName + " BANK_NAME BORROWER_NAME LUMP_SUM_AMOUNT EMI_NO");
        }

        try {
            Integer.parseInt(commandTokens[3]);
            Integer.parseInt(commandTokens[4]);
        } catch (NumberFormatException e) {
            throw new InvalidCommandFormatException("Invalid data types in command. Expected types are:" +
                " LUMP_SUM_AMOUNT(integer) EMI_NO(integer)");
        }
    }

    @Override
    public Optional<String> handle(String[] commandTokens) {
        String bankName = commandTokens[1];
        String customerName = commandTokens[2];
        CustomerIdentity customerIdentity = new CustomerIdentity(bankName, customerName);
        if (!loanRegistry.hasLoan(customerIdentity)) {
            throw new NoLoanExistsException("Payment can't be done because no loan exists for customer: " + customerIdentity);
        }

        long payment = Integer.parseInt(commandTokens[3]);
        int postEmi = Integer.parseInt(commandTokens[4]);
        paymentRegistry.register(customerIdentity, payment, postEmi);
        return Optional.empty();
    }
}
