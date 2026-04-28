package org.midheaven.application.store.tables;

public interface QueryWhereColumnConstraint {
    
    interface Text {
        void contains(CharSequence text);
        void startsWith(CharSequence text);
        void endsWith(CharSequence text);
    }
    
    void eq(Object value);
    void in(Iterable<?> values);
    void isNull();
    QueryWhereColumnConstraint not();
    
    QueryWhereColumnConstraint.Text text();
    
}
