package org.midheaven.application.store.tables;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapTableRegister implements TableRegister {
    
    private final Map<String , TableMetadata> tables = new ConcurrentHashMap<>();
    
    @Override
    public TableRegister register(TableMetadata tableMetadata) {
        tables.put(tableMetadata.logicName(), tableMetadata);
        return this;
    }
    
    @Override
    public TableMetadata tableOf(String tableName) {
        return tables.get(tableName);
    }
    
    @Override
    public Collection<TableMetadata> all() {
        return Collections.unmodifiableCollection(tables.values());
    }
}
