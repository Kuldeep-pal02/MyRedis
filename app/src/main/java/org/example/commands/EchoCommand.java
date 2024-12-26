package org.example.commands;

public class EchoCommand implements IRedisCommand{
    @Override
    public String execute(String[] args) {
        if (args.length < 1) {
            return "-ERR wrong number of arguments for 'echo' command\r\n";
        }
        return "$" + args[0].length() + "\r\n" + args[0] + "\r\n";
    }
}
