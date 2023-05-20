package org.example;

import org.example.command.CachingCommandHandlerFactory;
import org.example.command.CommandHandler;
import org.example.exception.InvalidCommandException;
import org.example.registry.CachingRegistryFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class FileParser {
    private final CachingCommandHandlerFactory handlerFactory;

    public FileParser() {
        CachingRegistryFactory registryFactory = new CachingRegistryFactory();
        this.handlerFactory = new CachingCommandHandlerFactory(registryFactory);
    }

    public Optional<String> parse(String line) {
        String[] tokens = line.split(" ");
        CommandHandler commandHandler = handlerFactory.getCommandHandler(tokens[0]);
        if (commandHandler == null) {
            throw new InvalidCommandException("Command with name: " + tokens[0] + " not found");
        }
        commandHandler.validate(tokens);
        return commandHandler.handle(tokens);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected exactly one input argument to be provided");
        }

        Path filePath = Paths.get(args[0]);
        FileParser fileParser = new FileParser();
        try (Stream<String> filestream = Files.lines(filePath)) {
            filestream.forEach(line -> fileParser.parse(line).ifPresent(System.out::println));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
