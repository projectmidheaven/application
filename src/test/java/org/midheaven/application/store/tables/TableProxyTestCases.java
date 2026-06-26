package org.midheaven.application.store.tables;

import org.junit.jupiter.api.Test;
import org.midheaven.application.network.Email;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TableProxyTestCases {
    
    EditableTableMetadata meta = new EditableTableMetadata("test")
                                     .addColumn(new EditableColumnMetadata("id", ColumnType.IDENTIFIER).isPrimaryKey(true))
                                     .addColumn(new EditableColumnMetadata("name", ColumnType.TEXT))
                                     .addColumn(new EditableColumnMetadata("email", ColumnType.TEXT))
                                     .addColumn(new EditableColumnMetadata("gender", ColumnType.CHOICE))
        ;
    
    @Test
    public void canReadFromProxy() {
        
  
        var table = new MapTableRow(meta);
        
        var data = table.as(TestData.class);
        
        data.setId(1L);
        data.setName("test");
        
        var row = (TableRow)data;
        
        assertEquals(data.getName(), row.get(meta.column("name")));
        
        row.set(meta.column("name"), "other");
        assertEquals(data.getName(), row.get(meta.column("name")));
        assertEquals("other", data.getName());
    }
    
    @Test
    public void blockNonPrimitiveValues() {
        var table = new MapTableRow(meta);
        
        var data = table.as(TestData.class);
        
        assertThrows(IllegalArgumentException.class , () -> data.setEmail(Email.parse("e@mail.com")));
        
    }
}

