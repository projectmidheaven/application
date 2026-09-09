package org.midheaven.application.locks;


public interface LockService {
    LockAcquisition acquire(Lock lock);
}
