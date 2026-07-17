package org.midheaven.application.store.tables;

import java.util.Map;

public interface TableRow {
    
    <D> D as(Class<D> dataClass);
    
    TableMetadata metadata();
    
     void set(ColumnMetadata column, Object value);
    
     Object get(ColumnMetadata column);
    
     Map<String, Object> asMap();
     
     default void setId(Object value){
         set(metadata().primaryColumn(), value);
     }
    
    default Object getId(){
       return get(metadata().primaryColumn());
    }
}
