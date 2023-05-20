package org.example.registry;

public class CachingRegistryFactory {
    private final LoanRegistry loanRegistry;
    private final BulkPaymentRegistry paymentRegistry;

    public CachingRegistryFactory() {
        loanRegistry = new LoanRegistry();
        paymentRegistry = new BulkPaymentRegistry();
    }

    public LoanRegistry getLoanRegistry() {
        return loanRegistry;
    }

    public BulkPaymentRegistry getPaymentRegistry() {
        return paymentRegistry;
    }
}
