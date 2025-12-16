package org.midheaven.application.transaction;

public interface TransactionService {
    
    Transaction newTransaction();
    Transaction newReadOnlyTransaction();
}
