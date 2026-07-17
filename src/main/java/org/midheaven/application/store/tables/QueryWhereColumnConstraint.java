package org.midheaven.application.store.tables;

public interface QueryWhereColumnConstraint {
    
    interface TextConstraints {
        void contains(CharSequence text);
        void startsWith(CharSequence text);
        void endsWith(CharSequence text);
    }
    
    interface ComparableConstraints {
        
        void isLessThan(Comparable<?> value);
        void isLessThanOrEqualTo(Comparable<?> value);
        void isGreaterThan(Comparable<?> value);
        void isGreaterThanOrEqualTo(Comparable<?> value);
        
    }
    
    void eq(Object value);
    void in(Iterable<?> values);
    void isNull();
    QueryWhereColumnConstraint not();
    
    TextConstraints text();
    ComparableConstraints value();
}
