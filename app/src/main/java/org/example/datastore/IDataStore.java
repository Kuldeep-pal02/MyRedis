package org.example.datastore;

public interface IDataStore<K, CustomMyRedisObject> {

    void add( K key, CustomMyRedisObject value);
    CustomMyRedisObject  get( K key );
}
