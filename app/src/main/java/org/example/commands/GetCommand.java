package org.example.commands;

import org.example.datastore.CustomMyRedisObject;
import org.example.datastore.IDataStore;
import org.example.resp.RESPUtils;


public class GetCommand implements IRedisCommand {
    private final IDataStore<String, CustomMyRedisObject> store;

    public GetCommand(IDataStore<String, CustomMyRedisObject> store) {
        this.store = store;
    }

    @Override
    public String execute(String[] args) {
        if (args.length < 1) {
            return RESPUtils.encodeError("ERR wrong number of arguments for 'GET' command");
            //return "-ERR wrong number of arguments for 'get' command\r\n";
        }
        CustomMyRedisObject value = store.get(args[0]);
        if (value == null) {
            return RESPUtils.encodeSimpleString( null );
            //return "$-1\r\n";
        }
        return "$" + value.getLength() + "\r\n" + value.getObj() + "\r\n";
    }
}
