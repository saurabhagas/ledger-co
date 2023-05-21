package org.example.command;

import org.example.exception.InvalidCommandFormatException;
import org.example.exception.LoanAlreadyExistsException;
import org.example.registry.CachingRegistryFactory;
import org.example.registry.LoanRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoanCommandHandlerTest {
    private LoanCommandHandler commandHandler;

    @BeforeEach
    public void setUp() {
        initMocks(false);
    }

    @Test
    public void testValidate_excessTokens() {
        assertThrows(InvalidCommandFormatException.class, () -> commandHandler.validate("LOAN IDIDI Dale 5000 1 6 BLAH".split(" ")));
    }

    @Test
    public void testValidate_insufficientTokens() {
        assertThrows(InvalidCommandFormatException.class, () -> commandHandler.validate("LOAN IDIDI Dale 5000".split(" ")));
    }

    @Test
    public void testValidate_invalidTypeForAmount() {
        assertThrows(InvalidCommandFormatException.class, () -> commandHandler.validate("LOAN IDIDI Dale five-thousand 1 6".split(" ")));
    }

    @Test
    public void testValidate_invalidTypeForYear() {
        assertThrows(InvalidCommandFormatException.class, () -> commandHandler.validate("LOAN IDIDI Dale 5000 one 6".split(" ")));
    }

    @Test
    public void testValidate_invalidTypeForInterest() {
        assertThrows(InvalidCommandFormatException.class, () -> commandHandler.validate("LOAN IDIDI Dale 5000 1 six".split(" ")));
    }

    @Test
    public void testValidate_success() {
        commandHandler.validate("LOAN IDIDI Dale 5000 1 6".split(" "));
    }

    @Test
    public void testHandle_loanAlreadyExists() {
        initMocks(true);
        assertThrows(LoanAlreadyExistsException.class, () -> commandHandler.handle("LOAN IDIDI Dale 5000 1 6".split(" ")));
    }

    @Test
    public void testHandle_success() {
        Optional<String> result = commandHandler.handle("LOAN IDIDI Dale 5000 1 6".split(" "));
        assertEquals(Optional.empty(), result);
    }

    private void initMocks(boolean hasLoan) {
        CachingRegistryFactory registryFactory = mock(CachingRegistryFactory.class);
        LoanRegistry mockLoanRegistry = mock(LoanRegistry.class);
        when(mockLoanRegistry.hasLoan(any())).thenReturn(hasLoan);
        when(registryFactory.getLoanRegistry()).thenReturn(mockLoanRegistry);
        commandHandler = new LoanCommandHandler("LOAN", registryFactory);
    }
}
