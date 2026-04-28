package org.midheaven.application.store.tables;

import java.util.Collection;

public interface TableRegister {
    
    TableRegister register(TableMetadata tableMetadata);

    TableMetadata tableOf(String name);
    
    Collection<TableMetadata> all();
}
