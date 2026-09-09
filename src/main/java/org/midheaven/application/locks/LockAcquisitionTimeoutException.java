package org.midheaven.application.locks;

public class LockAcquisitionTimeoutException extends LockAcquisitionException {
    
    LockAcquisitionTimeoutException() {
        super("The lock was not acquired in time");
    }
    
    LockAcquisitionTimeoutException(String message) {
        super(message);
    }
}
