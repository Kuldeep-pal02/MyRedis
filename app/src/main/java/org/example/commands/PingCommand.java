package org.example.commands;

import org.example.resp.RESPUtils;

public class PingCommand implements IRedisCommand{


    @Override
    public String execute(String[] args) {

        return RESPUtils.encodeSimpleString( "PONG");

    }
}
