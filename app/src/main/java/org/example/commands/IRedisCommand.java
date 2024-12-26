package org.example.commands;

public interface IRedisCommand {
    String execute(String[] args);
}
