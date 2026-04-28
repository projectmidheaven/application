package org.midheaven.application.store.tables;

import org.midheaven.lang.Maybe;
import org.midheaven.lang.NotNullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ColumnMetadata {
    
    String logicName();
    String physicalName();
    ColumnType type();
    boolean isRequired();
    boolean isPrimary();
    boolean isUnique();
    @NotNullable
    Maybe<Integer> maxLength();
    @NotNullable
    Maybe<Integer> minLength();
  
    default Object readFrom(ResultSet rs) throws SQLException {
        return rs.getObject(this.physicalName());
    }
    
    default void writeTo(TableRow row, Object value){
        row.set(this, value);
    }
    
}
