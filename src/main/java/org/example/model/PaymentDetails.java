package org.example.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDetails that = (PaymentDetails) o;
        return payment == that.payment && postEmi == that.postEmi;
    }

    @Override
    public int hashCode() {
        return Objects.hash(payment, postEmi);
    }

    @Override
    public String toString() {
        return "PaymentDetails{" +
            "payment=" + payment +
            ", postEmi=" + postEmi +
            '}';
    }
}
