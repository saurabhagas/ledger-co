package org.example.model;

import java.util.Objects;

public class LoanDetails {
    private final long dueAmount;
    private final int emis;
    private final long emiVal;

    public LoanDetails(long dueAmount, int emis, long emiVal) {
        this.dueAmount = dueAmount;
        this.emis = emis;
        this.emiVal = emiVal;
    }

    public long getDueAmount() {
        return dueAmount;
    }

    public long getEmiVal() {
        return emiVal;
    }

    public int getEmis() {
        return emis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanDetails that = (LoanDetails) o;
        return dueAmount == that.dueAmount && emis == that.emis && emiVal == that.emiVal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dueAmount, emis, emiVal);
    }

    @Override
    public String toString() {
        return "LoanDetails{" +
            "dueAmount=" + dueAmount +
            ", emis=" + emis +
            ", emiVal=" + emiVal +
            '}';
    }
}