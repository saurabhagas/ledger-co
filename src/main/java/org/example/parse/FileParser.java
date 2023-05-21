package org.example.parse;

import org.example.command.CachingCommandHandlerFactory;
import org.example.command.CommandHandler;
import org.example.exception.InvalidCommandException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class FileParser {
    private final CachingCommandHandlerFactory handlerFactory;

    public FileParser(CachingCommandHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    public void parseFile(Path filePath) throws IOException {
        try (Stream<String> filestream = Files.lines(filePath)) {
            filestream.forEach(line -> parseLine(line).ifPresent(System.out::println));
        }
    }

    Optional<String> parseLine(String line) {
        String[] tokens = line.split(" ");
        CommandHandler commandHandler = handlerFactory.getCommandHandler(tokens[0]);
        if (commandHandler == null) {
            throw new InvalidCommandException("Command with name: " + tokens[0] + " not found");
        }
        commandHandler.validate(tokens);
        return commandHandler.handle(tokens);
    }
}
