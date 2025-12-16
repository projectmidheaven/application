package org.midheaven.application.transaction;

public interface Transaction extends AutoCloseable{
    
    void commit();

    void close(); // declared again to remove throws clause
}
