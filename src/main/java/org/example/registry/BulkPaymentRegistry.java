package org.example.registry;

import org.example.model.CustomerIdentity;
import org.example.model.PaymentDetails;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BulkPaymentRegistry {
    private final Map<CustomerIdentity, Set<PaymentDetails>> registry = new HashMap<>();

    public void register(String bankName, String customerName, long payment, int postEmi) {
        CustomerIdentity customerIdentity = new CustomerIdentity(bankName, customerName);
        Set<PaymentDetails> existingPayments = registry.getOrDefault(customerIdentity, new HashSet<>());
        Set<PaymentDetails> updatedPayments = new HashSet<>(existingPayments);
        updatedPayments.add(new PaymentDetails(payment, postEmi));
        registry.put(customerIdentity, updatedPayments);
    }

    public long getPaymentsOnOrBefore(CustomerIdentity customerIdentity, int emiNo) {
        return registry.get(customerIdentity)
            .stream()
            .filter(paymentDetails -> paymentDetails.getPostEmi() <= emiNo)
            .mapToLong(PaymentDetails::getPayment)
            .sum();
    }
}
