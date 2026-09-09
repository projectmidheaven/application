package org.midheaven.application.locks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocksTestCases {
    
    enum LOCKS implements Lock.RefinableLock{
        TEST;
    }
    
    Lock.RefinableLock LOCK = Lock.named("testLock");
    
    LockService lockService = new MemoryLockService();
    
    @Test
    public void lockCalls(){
    
        assertThrows(IllegalStateException.class, () -> {
            lockService.acquire(Lock.named("test").over(23)).execute(() -> {
                throw new IllegalStateException();
            });
        });
        
        assertThrows(IllegalStateException.class, () -> {
            lockService.acquire(Lock.named("test").over(23)).call(() -> {
                throw new IllegalStateException();
            });
        });
    }
}
