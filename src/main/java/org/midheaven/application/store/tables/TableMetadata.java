package org.midheaven.application.store.tables;

import org.midheaven.collections.Enumerable;
import org.midheaven.lang.Strings;
import org.midheaven.lang.reflection.InvocationHandler;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface TableMetadata {
    
    default TableRow newRow(){
        return new MapTableRow(this);
    }
    
    String logicName();
    String physicalName();
    Enumerable<ColumnMetadata> columns();
    ColumnMetadata column(String name);
    ColumnMetadata primaryColumn();
    Enumerable<ColumnMetadata> columnsByPrefix(String prefix);
}

class RowInvocationAdapter implements InvocationHandler {
    
    private final TableRow tableRow;

    public RowInvocationAdapter(TableRow tableRow) {
        this.tableRow = tableRow;
    }
    
    @Override
    public Object handleInvocation(Object o, Method method, Object[] objects) throws Throwable {
        var name = method.getName();
        if (name.startsWith("get")){
            if(name.length() == 3){
                // tableRow method
                return tableRow.get((ColumnMetadata) objects[0]);
            } else{
                // bean method
                name = Strings.transform(name.substring(3), Strings.Casing.PASCAL, Strings.Casing.CAMEL);
                return  method.getReturnType().cast((tableRow.get(tableRow.metadata().column(name))));
            }
        } else if (name.startsWith("set")){
            if(name.length() == 3){
                // tableRow method
                tableRow.set((ColumnMetadata) objects[0], objects[1]);
                return null;
            } else {
                // bean method
                name = Strings.transform(name.substring(3), Strings.Casing.PASCAL, Strings.Casing.CAMEL);
                var column = tableRow.metadata().column(name);
                tableRow.set(column, ensureCorrectType(column, objects[0]));
                return null;
            }
         
        } else if (name.startsWith("is")){
            name = Strings.transform(name.substring(2), Strings.Casing.PASCAL, Strings.Casing.CAMEL);
            return  method.getReturnType().cast((tableRow.get(tableRow.metadata().column(name))));
        } else if (name.equals("metadata")){
            return tableRow.metadata();
        } else if (name.equals("asMap")){
            return tableRow.asMap();
        }
        
        throw new IllegalStateException("unrecognized call");
    }
    
    private Object ensureCorrectType(ColumnMetadata column, Object value) {
       if(value == null){
           return null;
       }
       try {
           return switch (column.type()) {
               case IDENTIFIER , FOREIGN_IDENTIFIER -> value;
               case TEXT, MEMO -> (String) value;
               case COUNT, CHOICE -> (Number) value;
               case DATE -> (LocalDate) value;
               case TIME -> (LocalTime) value;
               case DATETIME -> (LocalDateTime) value;
               case LOGICAL -> (Boolean) value;
               case NUMERIC -> (BigDecimal) value;
           };
       }catch (ClassCastException e){
           throw new IllegalArgumentException("Type " + value.getClass() + " is not compatible with column type " + column.type() );
       }
    }
    
}