package org.example.command;

import org.example.exception.InvalidCommandFormatException;
import org.example.exception.NoLoanExistsException;
import org.example.model.CustomerIdentity;
import org.example.model.LoanDetails;
import org.example.registry.BulkPaymentRegistry;
import org.example.registry.CachingRegistryFactory;
import org.example.registry.LoanRegistry;

import java.util.Optional;

public class BalanceCommandHandler implements CommandHandler {
    private final String commandName;
    private final LoanRegistry loanRegistry;
    private final BulkPaymentRegistry paymentRegistry;

    public BalanceCommandHandler(String commandName, CachingRegistryFactory registryFactory) {
        this.commandName = commandName;
        this.loanRegistry = registryFactory.getLoanRegistry();
        this.paymentRegistry = registryFactory.getPaymentRegistry();
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public void validate(String[] commandTokens) {
        if (commandTokens.length != 4) {
            throw new InvalidCommandFormatException("Invalid command format. Expected format is:" +
                commandName + " BANK_NAME BORROWER_NAME EMI_NO");
        }

        try {
            Integer.parseInt(commandTokens[3]);
        } catch (NumberFormatException e) {
            throw new InvalidCommandFormatException("Invalid data types in command. Expected types are:" +
                " EMI_NO(integer)");
        }
    }

    @Override
    public Optional<String> handle(String[] commandTokens) {
        String bankName = commandTokens[1];
        String customerName = commandTokens[2];
        CustomerIdentity customerIdentity = new CustomerIdentity(bankName, customerName);
        if (!loanRegistry.hasLoan(customerIdentity)) {
            throw new NoLoanExistsException("Balance can't be checked because no loan exists for customer: " + customerIdentity);
        }

        int emiNo = Integer.parseInt(commandTokens[3]);
        LoanDetails loanDetails = loanRegistry.getLoanDetails(customerIdentity);
        long paidViaBulkPayments = paymentRegistry.getPaymentsOnOrBefore(customerIdentity, emiNo);
        long totalPaid = emiNo * loanDetails.getEmiVal() + paidViaBulkPayments;
        int remEmis = (int) Math.ceil((loanDetails.getDueAmount() - totalPaid) / (double) loanDetails.getEmiVal());
        return Optional.of(bankName + " " + customerName + " " + totalPaid + " " + remEmis);
    }
}
