package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LoanDetailsTest {
    @Test
    void testEqualityAndSamenessWhenObjectsAreSame() {
        LoanDetails obj1 = new LoanDetails(100, 2);
        LoanDetails obj2 = obj1;
        assertEquals(obj1, obj2);
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    void testEqualityAndSamenessWhenObjectContentsAreEqual() {
        LoanDetails obj1 = new LoanDetails(100, 2);
        LoanDetails obj2 = new LoanDetails(100, 2);
        assertEquals(obj1, obj2);
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    void testEqualityAndSamenessWhenObjectContentsAreDifferent() {
        LoanDetails obj1 = new LoanDetails(100, 2);
        LoanDetails obj2 = new LoanDetails(200, 2);
        assertNotEquals(obj1, obj2);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
    }
}
