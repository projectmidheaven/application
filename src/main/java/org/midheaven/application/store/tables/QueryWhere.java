package org.midheaven.application.store.tables;

public interface QueryWhere {
    
    QueryWhereColumnConstraint column(String columnName);
    QueryWhereColumnConstraint column(ColumnMetadata columnMetadata);
}
