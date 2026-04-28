package org.midheaven.application.store.tables;

public class ValueConstraint implements ColumnConstraint {
    
    private final ColumnMetadata columnMetadata;
    private final ValueMatchOperator operator;
    private final Object value;
    
    public ValueConstraint(ColumnMetadata columnMetadata, ValueMatchOperator operator, Object value) {
        this.columnMetadata = columnMetadata;
        this.operator = operator;
        this.value = value;
    }
    
    public ColumnMetadata columnMetadata() {
        return columnMetadata;
    }
    
    public ValueMatchOperator operator() {
        return operator;
    }
    
    public Object value() {
        return value;
    }
}
