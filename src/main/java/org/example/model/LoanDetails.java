package org.example.model;

import java.util.Objects;

public class LoanDetails {
    private final long dueAmount;
    private final long emiVal;

    public LoanDetails(long dueAmount, long emiVal) {
        this.dueAmount = dueAmount;
        this.emiVal = emiVal;
    }

    public long getDueAmount() {
        return dueAmount;
    }

    public long getEmiVal() {
        return emiVal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanDetails that = (LoanDetails) o;
        return dueAmount == that.dueAmount && emiVal == that.emiVal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dueAmount, emiVal);
    }
}
