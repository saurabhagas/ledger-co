package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CustomerIdentityTest {
    @Test
    void testEqualityAndSamenessWhenObjectsAreSame() {
        CustomerIdentity obj1 = new CustomerIdentity("TEST_BANK", "TEST_CUSTOMER");
        CustomerIdentity obj2 = obj1;
        assertEquals(obj1, obj2);
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    void testEqualityAndSamenessWhenObjectContentsAreEqual() {
        CustomerIdentity obj1 = new CustomerIdentity("TEST_BANK", "TEST_CUSTOMER");
        CustomerIdentity obj2 = new CustomerIdentity("TEST_BANK", "TEST_CUSTOMER");
        assertEquals(obj1, obj2);
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    void testEqualityAndSamenessWhenObjectContentsAreDifferent() {
        CustomerIdentity obj1 = new CustomerIdentity("TEST_BANK", "TEST_CUSTOMER");
        CustomerIdentity obj2 = new CustomerIdentity("TEST_BANK_2", "TEST_CUSTOMER");
        assertNotEquals(obj1, obj2);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
    }
}
