package org.example.datastore;

import java.util.HashMap;
import java.util.Map;

public class HashMapDataStore implements  IDataStore<String, CustomMyRedisObject>{


    private final Map<String, CustomMyRedisObject> map = new HashMap<>();

    @Override
    public void add(String key, CustomMyRedisObject value) {
        map.put(key, value);
    }

//    @Override
//    public void add(String key, CustomMyRedisObject value, long ttl) {
//        value.setTtl(System.currentTimeMillis() + ttl);
//        map.put(key, value);
//    }

    @Override
    public CustomMyRedisObject get(String key) {
        CustomMyRedisObject redisObject = map.get(key);

        // Check if the TTL has expired
        if (redisObject != null && redisObject.getTtl() > 0) {
            long currTime = System.currentTimeMillis();
            boolean isExpired =  currTime > redisObject.getTtl()  ;
            if ( isExpired ) {
                // Remove expired object
                map.remove(key);
                return null;
            }
        }

        return redisObject;
    }
}
