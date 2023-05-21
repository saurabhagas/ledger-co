package org.example.command;

import org.example.exception.InvalidCommandFormatException;
import org.example.exception.NoLoanExistsException;
import org.example.registry.CachingRegistryFactory;
import org.example.registry.LoanRegistry;
import org.example.registry.PaymentRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentCommandHandlerTest {
    private PaymentCommandHandler commandHandler;

    @BeforeEach
    public void setUp() {
        initMocks(true);
    }

    @Test
    public void testValidate_excessTokens() {
        assertThrows(InvalidCommandFormatException.class, () -> commandHandler.validate("PAYMENT IDIDI Dale 5000 1 BLAH".split(" ")));
    }

    @Test
    public void testValidate_insufficientTokens() {
        assertThrows(InvalidCommandFormatException.class, () -> commandHandler.validate("PAYMENT IDIDI Dale 5000".split(" ")));
    }

    @Test
    public void testValidate_invalidTypeForAmount() {
        assertThrows(InvalidCommandFormatException.class, () -> commandHandler.validate("PAYMENT IDIDI Dale five-thousand 1".split(" ")));
    }

    @Test
    public void testValidate_invalidTypeForEmi() {
        assertThrows(InvalidCommandFormatException.class, () -> commandHandler.validate("PAYMENT IDIDI Dale 5000 one".split(" ")));
    }

    @Test
    public void testValidate_success() {
        commandHandler.validate("PAYMENT IDIDI Dale 5000 1".split(" "));
    }

    @Test
    public void testHandle_loanDoesntExists() {
        initMocks(false);
        assertThrows(NoLoanExistsException.class, () -> commandHandler.handle("PAYMENT IDIDI Dale 5000 1".split(" ")));
    }

    @Test
    public void testHandle_success() {
        Optional<String> result = commandHandler.handle("PAYMENT IDIDI Dale 5000 1".split(" "));
        assertEquals(Optional.empty(), result);
    }

    private void initMocks(boolean hasLoan) {
        CachingRegistryFactory registryFactory = mock(CachingRegistryFactory.class);
        LoanRegistry mockLoanRegistry = mock(LoanRegistry.class);
        PaymentRegistry mockPaymentRegistry = mock(PaymentRegistry.class);
        when(mockLoanRegistry.hasLoan(any())).thenReturn(hasLoan);
        when(registryFactory.getLoanRegistry()).thenReturn(mockLoanRegistry);
        when(registryFactory.getPaymentRegistry()).thenReturn(mockPaymentRegistry);
        commandHandler = new PaymentCommandHandler("PAYMENT", registryFactory);
    }
}
