package org.midheaven.application.store.tables;

public interface QueryOrder {
    
    QueryOrderColumnOrderDirection column(String columnName);
    QueryOrderColumnOrderDirection column(ColumnMetadata column);
}
