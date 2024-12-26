package org.example.commands;

public class PingCommand implements IRedisCommand{


    @Override
    public String execute(String[] args) {
        return "+PONG\r\n";
    }
}
