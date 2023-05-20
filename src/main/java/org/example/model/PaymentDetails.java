package org.example.model;

public class PaymentDetails {
    private final long payment;
    private final int postEmi;

    public PaymentDetails(long payment, int postEmi) {
        this.payment = payment;
        this.postEmi = postEmi;
    }

    public long getPayment() {
        return payment;
    }

    public int getPostEmi() {
        return postEmi;
    }
}
