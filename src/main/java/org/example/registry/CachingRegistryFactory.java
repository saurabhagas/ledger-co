package org.example.registry;

public class CachingRegistryFactory {
    private final LoanRegistry loanRegistry;
    private final PaymentRegistry paymentRegistry;

    public CachingRegistryFactory() {
        loanRegistry = new LoanRegistry();
        paymentRegistry = new PaymentRegistry();
    }

    public LoanRegistry getLoanRegistry() {
        return loanRegistry;
    }

    public PaymentRegistry getPaymentRegistry() {
        return paymentRegistry;
    }
}
