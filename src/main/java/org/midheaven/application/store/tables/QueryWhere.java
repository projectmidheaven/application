package org.midheaven.application.store.tables;

public interface QueryWhere {
    
    QueryWhereColumnConstraint primaryColumn();
    QueryWhereColumnConstraint column(String columnName);
    QueryWhereColumnConstraint column(ColumnMetadata columnMetadata);
}
