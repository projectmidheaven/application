package org.midheaven.application.store.tables;

import org.midheaven.collections.Assortment;
import org.midheaven.collections.Sequence;
import org.midheaven.lang.Maybe;

public interface Store {
    
    
    TableRow save(TableRow instance);
    
    default void saveAll(Assortment<? extends TableRow> instances){
        for(var instance : instances){
            save(instance);
        }
    }
    
    StoreQuerySearch search(TableMetadata metadata);
    
    Maybe<TableRow> findById(TableMetadata metadata, Object id);
    
    Sequence<TableRow> findByIds(TableMetadata metadata, Assortment<?> ids);
    
    void erase(TableMetadata metadata, Object id);
    default void erase(TableRow row){
        var id = row.get(row.metadata().primaryColumn());
        if (id != null){
            erase(row.metadata(), id);
        }
    }
    void erase(StoreQuery query);
}
