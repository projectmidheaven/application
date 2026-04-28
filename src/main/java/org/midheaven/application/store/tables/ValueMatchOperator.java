package org.midheaven.application.store.tables;

public class ValueMatchOperator {
    
    public static final ValueMatchOperator EQUALS = new ValueMatchOperator(ValueMatchOperation.EQUALITY, false);
    public static final ValueMatchOperator NULL = new ValueMatchOperator(ValueMatchOperation.NULL, false);
    public static final ValueMatchOperator CONTAINS_TEXT = new ValueMatchOperator(ValueMatchOperation.CONTAINS_TEXT, false);
    public static final ValueMatchOperator ENDS_WITH_TEXT = new ValueMatchOperator(ValueMatchOperation.ENDS_WITH_TEXT, false);
    public static final ValueMatchOperator STARTS_WITH_TEXT = new ValueMatchOperator(ValueMatchOperation.STARTS_WITH_TEXT, false);
    public static final ValueMatchOperator IN = new ValueMatchOperator(ValueMatchOperation.IN, false);
    
    private final boolean isNegated;
    private final ValueMatchOperation operation;
    
    private ValueMatchOperator(ValueMatchOperation operation, boolean isNegated){
        this.operation = operation;
        this.isNegated = isNegated;
    }
    
    public ValueMatchOperation operation(){
        return operation;
    }
    
    public boolean isNegated (){
        return isNegated;
    }
    
    public ValueMatchOperator negate(boolean negate) {
        if (negate){
            return new ValueMatchOperator(this.operation, !this.isNegated);
        }
        return this;
    }
}
