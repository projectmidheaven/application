package org.midheaven.application.connecting;

public class ConnectorException extends RuntimeException{
    
    protected ConnectorException(Throwable cause){
        super(cause);
    }
    
    protected ConnectorException(String message){
        super(message);
    }
}
