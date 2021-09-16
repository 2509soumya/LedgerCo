package com.ledgerco.command;

public interface CommandExecutor<A> {
    String getCommandId();
    String executeHelper(A input);
    void execute(Command command);
    boolean isValid(Command command);
}
