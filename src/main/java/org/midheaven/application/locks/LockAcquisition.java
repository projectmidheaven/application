package org.midheaven.application.locks;

import java.time.Duration;
import java.util.concurrent.Callable;

public interface LockAcquisition {
    
    void execute(Runnable runnable);
    <T> T call(Callable<T> callable);
    
    LockAcquisition before(Duration duration);
}
