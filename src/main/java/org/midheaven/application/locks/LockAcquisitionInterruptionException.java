package org.midheaven.application.locks;

public class LockAcquisitionInterruptionException extends LockAcquisitionException {
    
    LockAcquisitionInterruptionException(Throwable cause) {
        super(cause);
    }
}
