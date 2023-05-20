package org.example.model;

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
}