package org.example.command;

import org.example.registry.CachingRegistryFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CachingCommandHandlerFactoryTest {
    @Test
    void testObjectCreation() {
        CachingRegistryFactory registryFactory = Mockito.mock(CachingRegistryFactory.class);
        CachingCommandHandlerFactory factory = new CachingCommandHandlerFactory(registryFactory);
        assertEquals(LoanCommandHandler.class, factory.getCommandHandler("LOAN").getClass());
        assertEquals(PaymentCommandHandler.class, factory.getCommandHandler("PAYMENT").getClass());
        assertEquals(BalanceCommandHandler.class, factory.getCommandHandler("BALANCE").getClass());
    }
}