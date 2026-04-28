package org.midheaven.application.connecting;

public interface ConnectorConnection extends AutoCloseable {
    
    @Override
    void close() throws ConnectorException;
}
