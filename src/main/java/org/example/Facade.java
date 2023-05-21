package org.example;

import org.example.command.CachingCommandHandlerFactory;
import org.example.parse.FileParser;
import org.example.registry.CachingRegistryFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Facade {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected exactly one input argument to be provided");
        }

        Path filePath = Paths.get(args[0]);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File doesn't exist at path: " + filePath);
        }
        if (!Files.isReadable(filePath)) {
            throw new FileSystemException("File at path: " + filePath + " can't be read");
        }

        CachingRegistryFactory registryFactory = new CachingRegistryFactory();
        CachingCommandHandlerFactory handlerFactory = new CachingCommandHandlerFactory(registryFactory);
        new FileParser(handlerFactory).parseFile(filePath);
    }
}
