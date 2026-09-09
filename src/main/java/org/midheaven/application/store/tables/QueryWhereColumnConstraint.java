package org.midheaven.application.store.tables;

import org.midheaven.math.Interval;

import java.util.function.Consumer;

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
        void in(Interval<?> interval);
        
        void isNullOrLessThan(Comparable<?> value);
        void isNullOrLessThanOrEqualTo(Comparable<?> value);
        void isNullOrGreaterThan(Comparable<?> value);
        void isNullOrGreaterThanOrEqualTo(Comparable<?> value);
        void isNullOrIn(Interval<?> interval);
    }
    
    void eq(Object value);
    void in(Iterable<?> values);
    void isNull();
    QueryWhereColumnConstraint not();
    
    TextConstraints text();
    ComparableConstraints value();
    
    void join(String targetTableName, Consumer<QueryWhere> joinWhere);
    void join(String targetTableName, String targetColumnName, Consumer<QueryWhere> joinWhere);
}
