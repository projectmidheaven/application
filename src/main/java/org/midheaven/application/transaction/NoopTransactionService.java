package org.midheaven.application.transaction;

public final class NoopTransactionService implements TransactionService {
    
    private static final NoopTransaction NOOP_TRANSACTION = new NoopTransaction();
    
    @Override
    public Transaction newTransaction() {
        return NOOP_TRANSACTION;
    }
    
    @Override
    public Transaction newReadOnlyTransaction() {
        return new NoopTransaction();
    }
}

class NoopTransaction implements Transaction {
    
    @Override
    public void commit() {
        //no-op
    }
    
    @Override
    public void close() {
        //no-op
    }
}