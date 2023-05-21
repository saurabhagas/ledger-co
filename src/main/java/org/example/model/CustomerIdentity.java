package org.example.model;

import java.util.Objects;

public class CustomerIdentity {
    private final String bankName;
    private final String customerName;

    public CustomerIdentity(String bankName, String customerName) {
        this.bankName = bankName;
        this.customerName = customerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerIdentity that = (CustomerIdentity) o;
        return bankName.equals(that.bankName) && customerName.equals(that.customerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankName, customerName);
    }

    @Override
    public String toString() {
        return "CustomerIdentity{" +
            "bankName='" + bankName + '\'' +
            ", customerName='" + customerName + '\'' +
            '}';
    }
}
