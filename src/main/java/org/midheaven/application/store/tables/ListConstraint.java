package org.midheaven.application.store.tables;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListConstraint implements ColumnConstraint, Iterable<ColumnConstraint> {
    
    final List<ColumnConstraint> list = new ArrayList<>();
    final LogicOperator operator = LogicOperator.AND;
    
    public ListConstraint simplify() {
        if (list.size() == 1 && list.getFirst() instanceof ListConstraint listConstraint) {
            return listConstraint;
        }
        return this;
    }
    
    public LogicOperator operator (){
        return operator;
    }
    
    public boolean isEmpty(){
        return list.isEmpty();
    }
    
    @Override
    public Iterator<ColumnConstraint> iterator() {
        return list.iterator();
    }
}
