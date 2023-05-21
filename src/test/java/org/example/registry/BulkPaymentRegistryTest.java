package org.example.registry;

import org.example.model.CustomerIdentity;
import org.example.model.PaymentDetails;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BulkPaymentRegistryTest {
    private final PaymentRegistry paymentRegistry = new PaymentRegistry();
    private final CustomerIdentity customerIdentity = new CustomerIdentity("TEST_BANK", "TEST_CUSTOMER");

    @Test
    public void testRegisterPayment() {
        paymentRegistry.register(customerIdentity, 1000, 5);
        paymentRegistry.register(customerIdentity, 500, 10);
        Set<PaymentDetails> allPayments = paymentRegistry.getAllPayments(customerIdentity);
        Set<PaymentDetails> expected = new HashSet<>(Arrays.asList(new PaymentDetails(1000, 5), new PaymentDetails(500, 10)));
        assertEquals(expected, allPayments);
    }

    @Test
    public void testGetPaymentsOnOrBefore() {
        paymentRegistry.register(customerIdentity, 1000, 5);
        paymentRegistry.register(customerIdentity, 500, 10);
        assertEquals(0, paymentRegistry.getPaymentsOnOrBefore(customerIdentity, 4));
        assertEquals(1000, paymentRegistry.getPaymentsOnOrBefore(customerIdentity, 5));
        assertEquals(1000, paymentRegistry.getPaymentsOnOrBefore(customerIdentity, 9));
        assertEquals(1500, paymentRegistry.getPaymentsOnOrBefore(customerIdentity, 10));
    }
}
