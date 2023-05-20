package org.example.registry;

import org.example.exception.LoanAlreadyExistsException;
import org.example.model.CustomerIdentity;
import org.example.model.LoanDetails;

import java.util.HashMap;
import java.util.Map;

public class LoanRegistry {
    private final Map<CustomerIdentity, LoanDetails> registry;

    public LoanRegistry() {
        this.registry = new HashMap<>();
    }

    public void registerLoan(String bankName, String customerName, long principal, int years, int interestRate) throws LoanAlreadyExistsException {
        CustomerIdentity customerIdentity = new CustomerIdentity(bankName, customerName);
        if (hasLoan(customerIdentity)) {
            throw new LoanAlreadyExistsException("A loan already exists for customer: " + customerIdentity);
        }
        int emis = years * 12;
        long dueAmount = principal + (long) Math.ceil((principal * years * interestRate) / 100.0);
        long emiVal = (long) Math.ceil(dueAmount / (double) emis);
        LoanDetails loanDetails = new LoanDetails(dueAmount, emis, emiVal);
        registry.put(customerIdentity, loanDetails);
    }

    public boolean hasLoan(CustomerIdentity customerIdentity) {
        return getLoanDetails(customerIdentity) != null;
    }

    public LoanDetails getLoanDetails(CustomerIdentity customerIdentity) {
        return registry.get(customerIdentity);
    }
}
