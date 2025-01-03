package org.example.commands;

import org.example.advancedsa.bloomfilter.BloomFilter;
import org.example.datastore.CustomMyRedisObject;
import org.example.datastore.IDataStore;
import org.example.resp.RESPUtils;

public class BFExistCommand implements IRedisCommand {
    private final IDataStore<String, CustomMyRedisObject> dataStore;

    public BFExistCommand(IDataStore<String, CustomMyRedisObject> store) {
        this.dataStore = store;
    }


    @Override
    public String execute(String[] args) {
        if (args.length < 2) {
            return "-ERR wrong number of arguments for 'BF.EXISTS' command\r\n";
        }

        String key =  args[0];
        String element = args[1];

        CustomMyRedisObject customMyRedisObject = dataStore.get(key);
        try {
            BloomFilter bloomFilter = (BloomFilter) customMyRedisObject.getObj();
            if (bloomFilter == null) {
                return RESPUtils.encodeError("ERR Bloom Filter does not exist");
            }
            boolean result = bloomFilter.mightContains(element);
            return result ? ":1\r\n" : ":0\r\n";
        }catch (Exception e){
            return RESPUtils.encodeError( "ERR Key does not contain bloom filter, please pass correct key");
        }
    }
}
