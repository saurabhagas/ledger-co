package org.example.command;

import org.example.registry.CachingRegistryFactory;

import java.util.HashMap;
import java.util.Map;

public class CachingCommandHandlerFactory {
    private final Map<String, CommandHandler> commandHandlerMap = new HashMap<>();

    public CachingCommandHandlerFactory(CachingRegistryFactory registryFactory) {
        commandHandlerMap.put("LOAN", new LoanCommandHandler("LOAN", registryFactory));
        commandHandlerMap.put("PAYMENT", new PaymentCommandHandler("PAYMENT", registryFactory));
        commandHandlerMap.put("BALANCE", new BalanceCommandHandler("BALANCE", registryFactory));
    }

    public CommandHandler getCommandHandler(String command) {
        return commandHandlerMap.get(command);
    }
}
