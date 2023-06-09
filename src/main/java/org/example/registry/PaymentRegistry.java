package org.example.registry;

import org.example.model.CustomerIdentity;
import org.example.model.PaymentDetails;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PaymentRegistry {
    private final Map<CustomerIdentity, Set<PaymentDetails>> registry = new HashMap<>();

    public void register(CustomerIdentity customerIdentity, long payment, int postEmi) {
        Set<PaymentDetails> existingPayments = registry.getOrDefault(customerIdentity, Collections.emptySet());
        Set<PaymentDetails> updatedPayments = new HashSet<>(existingPayments);
        updatedPayments.add(new PaymentDetails(payment, postEmi));
        registry.put(customerIdentity, updatedPayments);
    }

    public long getPaymentsOnOrBefore(CustomerIdentity customerIdentity, int emiNo) {
        return registry.getOrDefault(customerIdentity, Collections.emptySet())
            .stream()
            .filter(paymentDetails -> paymentDetails.getPostEmi() <= emiNo)
            .mapToLong(PaymentDetails::getPayment)
            .sum();
    }

    Set<PaymentDetails> getAllPayments(CustomerIdentity customerIdentity) {
        return registry.get(customerIdentity);
    }
}
