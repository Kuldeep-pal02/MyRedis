package org.example.commands;

import org.example.advancedsa.bloomfilter.BloomFilter;
import org.example.datastore.CustomMyRedisObject;
import org.example.datastore.IDataStore;
import org.example.resp.RESPUtils;

public class BFReserveCommand implements IRedisCommand{

    private final IDataStore<String, CustomMyRedisObject> dataStore;

    public BFReserveCommand(IDataStore<String, CustomMyRedisObject> store) {
        this.dataStore = store;
    }


    @Override
    public String execute(String[] args) {
        if (args.length < 3) {
            return RESPUtils.encodeError( "ERR wrong number of arguments for 'BF.RESERVE' command" );
        }

        try {
            String key = args[0];
            double errorRate = Double.parseDouble(args[1]);
            int capacity = Integer.parseInt(args[2]);

            // Calculate bit array size and hash count based on parameters
            int bitSetSize = (int) Math.ceil(-(capacity * Math.log(errorRate)) / Math.pow(Math.log(2), 2));
            int hashFunctionCount = (int) Math.ceil((bitSetSize / capacity) * Math.log(2));

            BloomFilter bloomFilter = new BloomFilter(bitSetSize, hashFunctionCount);
            CustomMyRedisObject customMyRedisObject = new CustomMyRedisObject();
            customMyRedisObject.setObj( bloomFilter );
            dataStore.add(key, customMyRedisObject);

            return RESPUtils.encodeSimpleString( "OK" );
        } catch (NumberFormatException e) {
            return RESPUtils.encodeError( "ERR invalid argument" );
        }
    }
}
