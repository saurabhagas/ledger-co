package org.example.command;

import org.example.exception.InvalidCommandFormatException;
import org.example.exception.NoLoanExistsException;
import org.example.model.CustomerIdentity;
import org.example.model.LoanDetails;
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

public class BalanceCommandHandlerTest {
    private BalanceCommandHandler commandHandler;

    @BeforeEach
    public void setUp() {
        initMocks(true);
    }

    private void initMocks(boolean hasLoan) {
        CachingRegistryFactory registryFactory = mock(CachingRegistryFactory.class);
        LoanRegistry mockLoanRegistry = mock(LoanRegistry.class);
        PaymentRegistry mockPaymentRegistry = mock(PaymentRegistry.class);
        when(mockLoanRegistry.hasLoan(any())).thenReturn(hasLoan);
        when(registryFactory.getLoanRegistry()).thenReturn(mockLoanRegistry);
        when(registryFactory.getPaymentRegistry()).thenReturn(mockPaymentRegistry);
        commandHandler = new BalanceCommandHandler("BALANCE", registryFactory);
    }

    @Test
    public void testValidate_excessTokens() {
        assertThrows(InvalidCommandFormatException.class, () -> commandHandler.validate("BALANCE IDIDI Dale 1 BLAH".split(" ")));
    }

    @Test
    public void testValidate_insufficientTokens() {
        assertThrows(InvalidCommandFormatException.class, () -> commandHandler.validate("BALANCE IDIDI Dale".split(" ")));
    }

    @Test
    public void testValidate_invalidTypeForEmi() {
        assertThrows(InvalidCommandFormatException.class, () -> commandHandler.validate("BALANCE IDIDI Dale one".split(" ")));
    }

    @Test
    public void testValidate_success() {
        commandHandler.validate("BALANCE IDIDI Dale 1".split(" "));
    }

    @Test
    public void testHandle_loanDoesntExists() {
        initMocks(false);
        assertThrows(NoLoanExistsException.class, () -> commandHandler.handle("BALANCE IDIDI Dale 1".split(" ")));
    }

    @Test
    public void testHandle_noBulkPayments() {
        CachingRegistryFactory registryFactory = mock(CachingRegistryFactory.class);
        LoanRegistry mockLoanRegistry = mock(LoanRegistry.class);
        when(mockLoanRegistry.hasLoan(any())).thenReturn(true);
        when(mockLoanRegistry.getLoanDetails(any())).thenReturn(new LoanDetails(12000, 60, 200));
        when(registryFactory.getLoanRegistry()).thenReturn(mockLoanRegistry);

        PaymentRegistry mockPaymentRegistry = mock(PaymentRegistry.class);
        when(registryFactory.getPaymentRegistry()).thenReturn(mockPaymentRegistry);
        commandHandler = new BalanceCommandHandler("BALANCE", registryFactory);

        Optional<String> result = commandHandler.handle("BALANCE IDIDI Dale 5".split(" "));
        assertEquals(Optional.of("IDIDI Dale 1000 55"), result);
    }

    @Test
    public void testHandle_withBulkPaymentBeforeBalanceCheckEmi() {
        CachingRegistryFactory registryFactory = mock(CachingRegistryFactory.class);
        LoanRegistry mockLoanRegistry = mock(LoanRegistry.class);
        CustomerIdentity customerIdentity = new CustomerIdentity("IDIDI", "Dale");
        when(mockLoanRegistry.hasLoan(customerIdentity)).thenReturn(true);
        when(mockLoanRegistry.getLoanDetails(customerIdentity)).thenReturn(new LoanDetails(5300, 12, 442));
        when(registryFactory.getLoanRegistry()).thenReturn(mockLoanRegistry);

        PaymentRegistry mockPaymentRegistry = mock(PaymentRegistry.class);
        when(mockPaymentRegistry.getPaymentsOnOrBefore(customerIdentity, 5)).thenReturn(1000L);
        when(registryFactory.getPaymentRegistry()).thenReturn(mockPaymentRegistry);
        commandHandler = new BalanceCommandHandler("BALANCE", registryFactory);

        Optional<String> balanceBeforeBulkPayment = commandHandler.handle("BALANCE IDIDI Dale 3".split(" "));
        assertEquals(Optional.of("IDIDI Dale 1326 9"), balanceBeforeBulkPayment);
    }

    @Test
    public void testHandle_withBulkPaymentAlongWithBalanceCheckEmi() {
        CachingRegistryFactory registryFactory = mock(CachingRegistryFactory.class);
        LoanRegistry mockLoanRegistry = mock(LoanRegistry.class);
        CustomerIdentity customerIdentity = new CustomerIdentity("IDIDI", "Dale");
        when(mockLoanRegistry.hasLoan(customerIdentity)).thenReturn(true);
        when(mockLoanRegistry.getLoanDetails(customerIdentity)).thenReturn(new LoanDetails(5300, 12, 442));
        when(registryFactory.getLoanRegistry()).thenReturn(mockLoanRegistry);

        PaymentRegistry mockPaymentRegistry = mock(PaymentRegistry.class);
        when(mockPaymentRegistry.getPaymentsOnOrBefore(customerIdentity, 6)).thenReturn(1000L);
        when(registryFactory.getPaymentRegistry()).thenReturn(mockPaymentRegistry);
        commandHandler = new BalanceCommandHandler("BALANCE", registryFactory);

        Optional<String> balanceAfterBulkPayment = commandHandler.handle("BALANCE IDIDI Dale 6".split(" "));
        assertEquals(Optional.of("IDIDI Dale 3652 4"), balanceAfterBulkPayment);
    }
}
