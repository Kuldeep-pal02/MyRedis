package org.example.commands;

import org.example.datastore.CustomMyRedisObject;
import org.example.datastore.HashMapDataStore;
import org.example.datastore.IDataStore;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    private static final Map<String, IRedisCommand> commandMap = new HashMap<>();

    public static IDataStore<String, CustomMyRedisObject> dataStore;

//    public CommandRegistry(IDataStore<String, CustomMyRedisObject> dataStore) {
//        this.dataStore = dataStore;
//    }


    public static void registerCommand(String commandName, IRedisCommand command) {
        commandMap.put(commandName.toUpperCase(), command);
    }

    public static IRedisCommand getCommand(String commandName) {
        return commandMap.get(commandName.toUpperCase());
    }

    public static void  initCommandRegistry(){
        dataStore = new HashMapDataStore();
        registerCommand("PING", new PingCommand());
        registerCommand("ECHO", new EchoCommand());
        registerCommand("SET", new SetCommand( dataStore ));
        registerCommand("GET", new GetCommand( dataStore ));

        registerCommand("BF.RESERVE", new BFReserveCommand( dataStore ));
    }

}
