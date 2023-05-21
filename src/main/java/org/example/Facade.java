package org.example;

import org.example.command.CachingCommandHandlerFactory;
import org.example.parse.FileParser;
import org.example.registry.CachingRegistryFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Facade {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected exactly one input argument to be provided");
        }

        Path filePath = Paths.get(args[0]);
        if (!Files.isReadable(filePath)) {
            throw new FileNotFoundException("File at path: " + filePath + " doesn't exist or isn't readable");
        }

        CachingCommandHandlerFactory handlerFactory = new CachingCommandHandlerFactory(new CachingRegistryFactory());
        new FileParser(handlerFactory).parseFile(filePath);
    }
}
