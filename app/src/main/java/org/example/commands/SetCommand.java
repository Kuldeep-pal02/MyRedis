package org.example.commands;

import org.example.datastore.CustomMyRedisObject;
import org.example.datastore.IDataStore;
import org.example.resp.RESPUtils;

public class SetCommand implements IRedisCommand{

   IDataStore<String,CustomMyRedisObject> dataStore;

    @Override
    public String execute(String[] args) {
        if (args.length < 2) {
            // Return error if there aren't enough arguments
            return RESPUtils.encodeError("ERR wrong number of arguments for 'SET' command");
            //return "-ERR wrong number of arguments for 'SET' command\r\n";
        }

        try {
            String key = args[0];


            Object value = args[1];

            // Check if a TTL is provided
            if (args.length == 3) {
                long ttl;
                try {
                    ttl = Long.parseLong(args[2]);
                } catch (NumberFormatException e) {
                    return RESPUtils.encodeError("ERR invalid TTL value");
                    //return "-ERR invalid TTL value\r\n";
                }
                // Add the key-value pair with TTL
                CustomMyRedisObject redisObject = new CustomMyRedisObject();
                redisObject.setObj( value );
                redisObject.setTtl( ttl );
                dataStore.add(key, redisObject);
            } else {
                // Add the key-value pair without TTL
                CustomMyRedisObject redisObject = new CustomMyRedisObject();
                redisObject.setObj( value );
                dataStore.add(key, redisObject);
            }

            // Return success response
            return RESPUtils.encodeSimpleString("OK");
            //return "+OK\r\n";
        } catch (ClassCastException e) {
            return RESPUtils.encodeError("ERR invalid key or value type");
            //return "-ERR invalid key or value type\r\n";
        }
    }
    public SetCommand(IDataStore dataStore){
        this.dataStore = dataStore;
    }
}
