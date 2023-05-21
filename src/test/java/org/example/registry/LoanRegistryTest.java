package org.example.registry;

import org.example.model.CustomerIdentity;
import org.example.model.LoanDetails;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoanRegistryTest {
    private final LoanRegistry loanRegistry = new LoanRegistry();
    private final CustomerIdentity customerIdentity = new CustomerIdentity("TEST_BANK", "TEST_CUSTOMER");

    @Test
    public void testHasLoan() {
        assertFalse(loanRegistry.hasLoan(customerIdentity));
        loanRegistry.registerLoan(customerIdentity, 100, 2, 5);
        assertTrue(loanRegistry.hasLoan(customerIdentity));
    }

    @Test
    public void testRegisterLoan() {
        loanRegistry.registerLoan(customerIdentity, 10000, 5, 4);
        LoanDetails loanDetails = loanRegistry.getLoanDetails(customerIdentity);
        LoanDetails expectedLoanDetails = new LoanDetails(12000, 200);
        assertEquals(expectedLoanDetails, loanDetails);
    }

    @Test
    public void testRegisterLoanWithEmiApproximation() {
        loanRegistry.registerLoan(customerIdentity, 101, 4, 5);
        LoanDetails loanDetails = loanRegistry.getLoanDetails(customerIdentity);
        LoanDetails expectedLoanDetails = new LoanDetails(122, 3);
        assertEquals(expectedLoanDetails, loanDetails);
    }
}
