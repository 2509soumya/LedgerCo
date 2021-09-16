package com.ledgerco.command;

import com.ledgerco.exceptions.InvalidInputException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommandHandler {

    Map<String,CommandExecutor> commandExecutors=new HashMap<>();

    @Autowired
    public CommandHandler(ListableBeanFactory beanFactory) {
        Collection<CommandExecutor> handlers = beanFactory.getBeansOfType(CommandExecutor.class).values();
        handlers.forEach(handler -> commandExecutors.put(handler.getCommandId(), handler));
    }

    public void processCommand(Command command){
        if(commandExecutors.containsKey(command.getName()) && commandExecutors.get(command.getName()).isValid(command)){
            commandExecutors.get(command.getName()).execute(command);
        }else{
            throw new InvalidInputException(command.getName());
        }
    }

}
