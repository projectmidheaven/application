package org.midheaven.application.store.tables;

public class ValueConstraint implements ColumnConstraint {
    
    private final QualifiedColumn column;
    private final ValueMatchOperator operator;
    private final Object value;
    
    public ValueConstraint(QualifiedColumn column, ValueMatchOperator operator, Object value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }
    
    public QualifiedColumn column() {
        return column;
    }
    
    public ValueMatchOperator operator() {
        return operator;
    }
    
    public Object value() {
        return value;
    }
}
