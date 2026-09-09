package org.midheaven.application.store;

public class DeleteReferencedInstanceException extends StoreException {
    
    private final String tableName;
    private final String referenceTableName;
    
    public DeleteReferencedInstanceException(String tableName, String referenceTableName) {
        super("Cannot delete entity that is referred by other entity. Trying to delete " + tableName + " that is referred by " + referenceTableName);
        this.tableName = tableName;
        this.referenceTableName = referenceTableName;
    }
    
    public String tableName() {
        return tableName;
    }
    
    public String referenceTableName() {
        return referenceTableName;
    }
}
