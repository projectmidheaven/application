package org.midheaven.application.store.tables;

public interface QueryOrderColumnOrderDirection {
    
    void ascending();
    void descending();
    void order(SortOrder order);
}
