package org.midheaven.application.store;

public class DuplicatedUniqueColumnException extends StoreException {
    
    private final String tableName;
    private final String columnName;
    
    public DuplicatedUniqueColumnException(String tableName, String columnName) {
        super("Cannot duplicate a unique column. Column is : " + tableName + "." + columnName);
        this.tableName = tableName;
        this.columnName = columnName;
    }
    
    public String tableName() {
        return tableName;
    }
    
    public String columnName() {
        return columnName;
    }
}
