package org.midheaven.application.store.tables;

public interface CleanableStore extends Store{
    
    void clearAll();
    void clear(String tableName);
}
