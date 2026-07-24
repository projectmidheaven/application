package org.midheaven.application.store.tables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractStoreSearch implements StoreQuerySearch{
    
    protected final TableMetadata tableMetadata;
    protected final ListConstraint columnConstraint = new ListConstraint();
    protected final ListConstraint orderConstraint = new ListConstraint();
    private final TableRegister register;
    protected final Map<String, TableMetadata> aliasToTableMapping = new HashMap<>();
    protected final Map<String, String> tableToAliasMapping = new HashMap<>();
    protected final List<JoinOnConstraint> joins = new ArrayList<>();
    
    private char letter = 'A';
    protected AbstractStoreSearch(
        TableRegister register,
        TableMetadata tableMetadata
    ){
        this.register = register;
        this.tableMetadata = tableMetadata;
        newAlias(tableMetadata);
    }
    
    private String aliasOf(TableMetadata tableMetadata){
        return tableToAliasMapping.get(tableMetadata.logicName());
    }
    
    private String newAlias(TableMetadata tableMetadata){
        var alias = Character.toString(letter);
        aliasToTableMapping.put(alias,tableMetadata);
        tableToAliasMapping.put(tableMetadata.logicName(), alias);
        letter++;
        return alias;
    }
    
    @Override
    public StoreQuerySearch match(Consumer<QueryWhere> matcher) {
        var fieldsConstraintBlock = new QueryWhere(){
            
            @Override
            public QueryWhereColumnConstraint column(String columnName) {
                if (tableMetadata.column(columnName) == null){
                    throw new IllegalArgumentException("Column "  +  columnName + " is not in table " + tableMetadata.logicName());
                }
                var alias = aliasOf(tableMetadata);
                return new InnerQueryWhereFieldConstraint(new QualifiedColumn(alias,tableMetadata.column(columnName)), false);
            }
            
            @Override
            public QueryWhereColumnConstraint column(ColumnMetadata columnMetadata) {
                if (tableMetadata.column(columnMetadata.logicName()) == null){
                    throw new IllegalArgumentException("Column "  +  columnMetadata.logicName() + " is not in table " + tableMetadata.logicName());
                }
                var alias = aliasOf(tableMetadata);
                return new InnerQueryWhereFieldConstraint(new QualifiedColumn(alias,columnMetadata), false);
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
                        orderConstraint.list.add(new SortConstraint(new QualifiedColumn("A", column), order));
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
        
        private final QualifiedColumn column;
        private final boolean negated;
        
        public InnerQueryWhereFieldConstraint(QualifiedColumn column, boolean negated) {
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
        public TextConstraints text() {
            return new TextConstraints() {
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
        
        @Override
        public ComparableConstraints value() {
            return new ComparableConstraints() {
                @Override
                public void isLessThan(Comparable<?> value) {
                    columnConstraint.list.add(new ValueConstraint(column, ValueMatchOperator.LESS_THAN.negate(negated), value));
                }
                
                @Override
                public void isLessThanOrEqualTo(Comparable<?> value) {
                    columnConstraint.list.add(new ValueConstraint(column, ValueMatchOperator.LESS_THAN_OR_EQUAL.negate(negated), value));
                }
                
                @Override
                public void isGreaterThan(Comparable<?> value) {
                    columnConstraint.list.add(new ValueConstraint(column, ValueMatchOperator.GREATER_THAN.negate(negated), value));
                }
                
                @Override
                public void isGreaterThanOrEqualTo(Comparable<?> value) {
                    columnConstraint.list.add(new ValueConstraint(column, ValueMatchOperator.GREATER_THAN_OR_EQUAL.negate(negated), value));
                }
            };
        }
        
        @Override
        public void join(String tableName, Consumer<QueryWhere> joinWhere) {
            var joinTable = register.tableOf(tableName);
            var alias = newAlias(joinTable);
            
            joins.add(new JoinOnConstraint(column, new QualifiedColumn(alias, joinTable.primaryColumn()), ValueMatchOperator.EQUALS));
            
            var fieldsConstraintBlock = new QueryWhere(){
                
                @Override
                public QueryWhereColumnConstraint column(String columnName) {
                    if (joinTable.column(columnName) == null){
                        throw new IllegalArgumentException("Column "  +  columnName + " is not in table " + joinTable.logicName());
                    }
                    return new InnerQueryWhereFieldConstraint(new QualifiedColumn(alias, joinTable.column(columnName)), false);
                }
                
                @Override
                public QueryWhereColumnConstraint column(ColumnMetadata columnMetadata) {
                    if (joinTable.column(columnMetadata.logicName()) == null){
                        throw new IllegalArgumentException("Column "  +  columnMetadata.logicName() + " is not in table " + joinTable.logicName());
                    }
                    return new InnerQueryWhereFieldConstraint(new QualifiedColumn(alias, columnMetadata), false);
                }
            };
            
            joinWhere.accept(fieldsConstraintBlock);
        }
        
    }
}




