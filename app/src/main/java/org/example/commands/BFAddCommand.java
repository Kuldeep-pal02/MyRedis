package org.example.commands;

import org.example.advancedsa.bloomfilter.BloomFilter;
import org.example.datastore.CustomMyRedisObject;
import org.example.datastore.IDataStore;
import org.example.resp.RESPUtils;

public class BFAddCommand implements IRedisCommand {
    private final IDataStore<String, CustomMyRedisObject> dataStore;

    public BFAddCommand(IDataStore<String, CustomMyRedisObject> store) {
        this.dataStore = store;
    }


    @Override
    public String execute(String[] args) {
        if (args.length < 2) {
            return RESPUtils.encodeError("ERR wrong number of arguments for 'BF.ADD' command");
        }

        String key = args[0];
        String element = args[1];
        CustomMyRedisObject customMyRedisObject = dataStore.get(key);
        try {

        }catch (Exception e){

        }
        BloomFilter bloomFilter = (BloomFilter) customMyRedisObject.getObj();
        if (bloomFilter == null) {
            return RESPUtils.encodeError( "ERR Bloom Filter does not exist");
        }

        bloomFilter.add(element);
        return RESPUtils.encodeSimpleString("OK");

    }
}
