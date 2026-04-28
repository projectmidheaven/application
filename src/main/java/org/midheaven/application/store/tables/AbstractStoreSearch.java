package org.midheaven.application.store.tables;

import java.util.function.Consumer;

public abstract class AbstractStoreSearch implements StoreQuerySearch{
    
    protected final TableMetadata tableMetadata;
    protected final ListConstraint columnConstraint = new ListConstraint();
    protected final ListConstraint orderConstraint = new ListConstraint();
    
    protected AbstractStoreSearch(
        TableMetadata tableMetadata
    ){
        this.tableMetadata = tableMetadata;
    }
    
    @Override
    public StoreQuerySearch match(Consumer<QueryWhere> matcher) {
        var fieldsConstraintBlock = new QueryWhere(){
            
            @Override
            public QueryWhereColumnConstraint column(String columnName) {
                return new InnerQueryWhereFieldConstraint(tableMetadata.column(columnName), false);
            }
            
            @Override
            public QueryWhereColumnConstraint column(ColumnMetadata columnMetadata) {
                if (tableMetadata.column(columnMetadata.logicName()) == null){
                    throw new IllegalArgumentException("Column "  +  columnMetadata.logicName() + " is not of table " + tableMetadata.logicName());
                }
                return new InnerQueryWhereFieldConstraint(columnMetadata, false);
            }
        };
        
        matcher.accept(fieldsConstraintBlock);
        
        return this;
    }
    
    @Override
    public StoreQuerySearch sorted(Consumer<QueryOrder> order) {
        var columnOrderBlock = new QueryOrder(){
            
            @Override
            public QueryOrderColumnOrderDirection column(String columnName) {
               return column(tableMetadata.column(columnName));
            }
            
            @Override
            public QueryOrderColumnOrderDirection column(ColumnMetadata column) {
                return new QueryOrderColumnOrderDirection() {
                    @Override
                    public void ascending() {
                        order(SortOrder.ASCENDING);
                    }
                    
                    @Override
                    public void descending() {
                        order(SortOrder.DESCENDING);
                    }
                    
                    @Override
                    public void order(SortOrder order) {
                        orderConstraint.list.add(new SortConstraint(column, order));
                    }
                };
            }
        };
        
        order.accept(columnOrderBlock);
        
        return this;
    }
    
    @Override
    public abstract StoreQuery query();
    
    
    class InnerQueryWhereFieldConstraint implements QueryWhereColumnConstraint {
        
        private final ColumnMetadata column;
        private final boolean negated;
        
        public InnerQueryWhereFieldConstraint(ColumnMetadata column, boolean negated) {
            this.column = column;
            this.negated = negated;
        }
        
        @Override
        public void eq(Object value) {
            columnConstraint.list.add(new ValueConstraint(column, ValueMatchOperator.EQUALS.negate(negated), value));
        }
        
        @Override
        public void in(Iterable<?> values) {
            columnConstraint.list.add(new ValueConstraint(column, ValueMatchOperator.IN.negate(negated), values));
        }
        
        @Override
        public void isNull() {
            columnConstraint.list.add(new ValueConstraint(column, ValueMatchOperator.NULL.negate(negated), null));
        }
        
        @Override
        public QueryWhereColumnConstraint not() {
            return new InnerQueryWhereFieldConstraint(column, !negated);
        }
        
        @Override
        public Text text() {
            return new Text() {
                @Override
                public void contains(CharSequence text) {
                    columnConstraint.list.add(new ValueConstraint(column, ValueMatchOperator.CONTAINS_TEXT.negate(negated), text));
                }
                
                @Override
                public void startsWith(CharSequence text) {
                    columnConstraint.list.add(new ValueConstraint(column, ValueMatchOperator.STARTS_WITH_TEXT.negate(negated), text));
                }
                
                @Override
                public void endsWith(CharSequence text) {
                    columnConstraint.list.add(new ValueConstraint(column, ValueMatchOperator.ENDS_WITH_TEXT.negate(negated), text));
                }
            };
        }
        
    }
}




