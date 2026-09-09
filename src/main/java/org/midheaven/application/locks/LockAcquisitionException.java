package org.midheaven.application.locks;

import java.util.concurrent.TimeoutException;

public class LockAcquisitionException extends RuntimeException{
    
    public static LockAcquisitionException wrap(Exception other){
        if (other instanceof LockAcquisitionException lockAcquisitionException){
            return lockAcquisitionException;
        } else if (other instanceof InterruptedException interruptedException){
            return new LockAcquisitionInterruptionException(interruptedException);
        } else if (other instanceof TimeoutException timeoutException){
            return new LockAcquisitionTimeoutException(timeoutException.getMessage());
        }
        return new LockAcquisitionException(other);
    }
    
    LockAcquisitionException(Throwable cause){
        super(cause);
    }
    
    LockAcquisitionException(String message){
        super(message);
    }
}
