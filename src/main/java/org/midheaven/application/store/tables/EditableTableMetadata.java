package org.midheaven.application.store.tables;

import org.midheaven.collections.Association;
import org.midheaven.collections.Assortment;
import org.midheaven.collections.DistinctAssortment;
import org.midheaven.collections.ResizableAssociation;

public class EditableTableMetadata implements TableMetadata {
    
    private final String logicName;
    private final ResizableAssociation<String , EditableColumnMetadata> columns = Association.builder().concurrent().empty();
    private EditableColumnMetadata primaryColumn;
    private String physicalName;
    
    public EditableTableMetadata(String logicTableName){
        this.logicName = logicTableName;
        this.physicalName = TableCasing.toTableCasing(logicTableName);
    }
    
    @Override
    public String logicName() {
        return logicName;
    }
    
    @Override
    public String physicalName() {
        return physicalName;
    }
    
    public void setPhysicalName(String physicalName){
        this.physicalName = physicalName;
    }
    
    @Override
    public Assortment<ColumnMetadata> columns() {
        return DistinctAssortment.builder().from(columns.values());
    }
    
    @Override
    public EditableColumnMetadata column(String name) {
        return columns.getValue(name).orNull();
    }
    
    @Override
    public EditableColumnMetadata primaryColumn() {
        return primaryColumn;
    }
    
    public EditableTableMetadata addColumn(ColumnMetadata columnMetadata){
        EditableColumnMetadata editableColumn = new EditableColumnMetadata(columnMetadata);
        this.columns.putValue(columnMetadata.logicName(), editableColumn);
        if (columnMetadata.isPrimary()){
            if (this.primaryColumn != null){
                throw new IllegalStateException("Primary column was already set");
            }
            this.primaryColumn = editableColumn;
        }
        return this;
    }
}
