package org.example.parse;

import org.example.command.CachingCommandHandlerFactory;
import org.example.command.LoanCommandHandler;
import org.example.exception.InvalidCommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class FileParserTest {
    private FileParser fileParser;
    private LoanCommandHandler mockLoanCommandHandler;

    @BeforeEach
    public void setUp() {
        CachingCommandHandlerFactory handlerFactory = mock(CachingCommandHandlerFactory.class);
        mockLoanCommandHandler = mock(LoanCommandHandler.class);
        when(handlerFactory.getCommandHandler("LOAN")).thenReturn(mockLoanCommandHandler);
        fileParser = new FileParser(handlerFactory);
    }

    @Test
    public void testInvalidDelimiter() {
        assertThrows(InvalidCommandException.class, () -> fileParser.parseLine("LOAN\tIDIDI'TOM 5000 1\t6"));
    }

    @Test
    public void testInvalidCommand() {
        assertThrows(InvalidCommandException.class, () -> fileParser.parseLine("BLAH IDIDI TOM 5000"));
    }

    @Test
    public void testValidCommand() {
        String command = "LOAN IDIDI Dale 5000 1 6";
        String[] tokens = command.split(" ");
        fileParser.parseLine(command);
        verify(mockLoanCommandHandler).validate(tokens);
        verify(mockLoanCommandHandler).handle(tokens);
        verifyNoMoreInteractions(mockLoanCommandHandler);
    }
}
