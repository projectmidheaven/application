package org.midheaven.application.store;

public class StoreException extends RuntimeException{
    
    public static StoreException wrap(Throwable other){
        if (other instanceof StoreException storeException){
            return storeException;
        }
        return new StoreException(other);
    }
    
    protected StoreException(Throwable other) {
        super(other);
    }
    
    protected StoreException(String message) {
        super(message);
    }
}
