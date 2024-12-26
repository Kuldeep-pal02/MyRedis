package org.example.eviction;

import org.example.customlog.LoggerFactory;
import org.example.customlog.MyLogger;
import org.example.datastore.IDataStore;

public class LRUEviction implements IEvictionStrategy{

    private final IDataStore dataStore;

    private final MyLogger logger = LoggerFactory.getLoggerInstance();

    public LRUEviction(IDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void evict() {
        logger.logMessage( "LRUEviction is being called");
    }
}
