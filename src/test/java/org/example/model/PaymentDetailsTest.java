package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PaymentDetailsTest {
    @Test
    void testEqualityAndSamenessWhenObjectsAreSame() {
        PaymentDetails obj1 = new PaymentDetails(1000, 5);
        PaymentDetails obj2 = obj1;
        assertEquals(obj1, obj2);
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    void testEqualityAndSamenessWhenObjectContentsAreEqual() {
        PaymentDetails obj1 = new PaymentDetails(1000, 5);
        PaymentDetails obj2 = new PaymentDetails(1000, 5);
        assertEquals(obj1, obj2);
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    void testEqualityAndSamenessWhenObjectContentsAreDifferent() {
        PaymentDetails obj1 = new PaymentDetails(1000, 5);
        PaymentDetails obj2 = new PaymentDetails(10000, 5);
        assertNotEquals(obj1, obj2);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
    }
}
