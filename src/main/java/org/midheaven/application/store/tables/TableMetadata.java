package org.midheaven.application.store.tables;

import org.midheaven.collections.Assortment;
import org.midheaven.lang.Strings;
import org.midheaven.lang.reflection.InvocationHandler;

import java.lang.reflect.Method;

public interface TableMetadata {
    
    default TableRow newRow(){
        return new MapTableRow(this);
    }
    
    String logicName();
    String physicalName();
    Assortment<ColumnMetadata> columns();
    ColumnMetadata column(String name);
    ColumnMetadata primaryColumn();
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
            name = Strings.transform(name.substring(3), Strings.Casing.PASCAL, Strings.Casing.CAMEL);
            return convert(tableRow.metadata().column(name), method.getReturnType());
        } else if (name.startsWith("set")){
            name = Strings.transform(name.substring(3), Strings.Casing.PASCAL, Strings.Casing.CAMEL);
            tableRow.set(tableRow.metadata().column(name),objects[0]);
            return null;
        } else if (name.startsWith("is")){
            name = Strings.transform(name.substring(2), Strings.Casing.PASCAL, Strings.Casing.CAMEL);
            return convert(tableRow.metadata().column(name), method.getReturnType());
        } else if (name.equals("metadata")){
            return tableRow.metadata();
        } else if (name.equals("asMap")){
            return tableRow.asMap();
        }
        
        throw new IllegalStateException("unrecognized call");
    }
    
    private Object convert(Object value, Class<?> returnType) {
        if (value == null || returnType.isInstance(value)){
            return returnType.cast(value);
        }
//        if (conversionService != null){
//            if (Maybe.class.isAssignableFrom(returnType)){
//                return Maybe.of(conversionService.convert(value, returnType));
//            } else if (Optional.class.isAssignableFrom(returnType)){
//                return Optional.ofNullable(conversionService.convert(value, returnType));
//            }
//            return conversionService.convert(value, returnType);
//        }
        
        return returnType.cast(value);
    }
}