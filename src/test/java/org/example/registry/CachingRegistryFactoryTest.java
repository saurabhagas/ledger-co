package org.example.registry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CachingRegistryFactoryTest {
    @Test
    void testObjectCreation() {
        CachingRegistryFactory factory = new CachingRegistryFactory();
        assertNotNull(factory.getLoanRegistry());
        assertNotNull(factory.getPaymentRegistry());
    }
}