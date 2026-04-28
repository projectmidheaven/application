package org.midheaven.application.store.tables;

import org.midheaven.lang.reflection.Mirror;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

class MapTableRow implements TableRow {
    
    final TableMetadata metadata;
    final LinkedHashMap<String, Object> columnValues = new LinkedHashMap<>();
    
    MapTableRow(TableMetadata metadata){
        this.metadata = metadata;
    }
    
    public <D> D as(Class<D> dataClass){
        return dataClass.cast(Mirror.reflect(dataClass).proxy(new RowInvocationAdapter(this), TableRow.class));
    }
    
    public TableMetadata metadata() {
        return metadata;
    }
    
    public void set(ColumnMetadata column, Object value){
        columnValues.put(column.logicName(), value);
    }
    
    public Object get(ColumnMetadata column) {
        return columnValues.get(column.logicName());
    }
    
    public Map<String, Object> asMap() {
        var map = new HashMap<String, Object>();
        
        for (var entry : columnValues.entrySet()){
            map.put(metadata.column(entry.getKey()).physicalName(), entry.getValue());
        }
        return map;
    }
}
