package org.midheaven.application.store.tables;

public interface CleanableStore {
    
    void clearAll();
    void clear(String tableName);
}
