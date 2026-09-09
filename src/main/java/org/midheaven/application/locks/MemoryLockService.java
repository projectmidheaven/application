package org.midheaven.application.locks;

import org.midheaven.lang.Comparables;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class MemoryLockService implements LockService {
    
    private Map<String, java.util.concurrent.locks.Lock> lockMap = new ConcurrentHashMap<>();
    
    private java.util.concurrent.locks.Lock lockFor(Lock lock) {
        return lockMap.computeIfAbsent(lock.name(), k -> new java.util.concurrent.locks.ReentrantLock());
    }
    
    @Override
    public LockAcquisition acquire(Lock lock) {
        return new MemoryLockAcquisition(lockFor(lock));
    }
    
}


class MemoryLockAcquisition implements LockAcquisition {
    
    private final java.util.concurrent.locks.Lock concurrentLock;
    
    MemoryLockAcquisition(java.util.concurrent.locks.Lock concurrentLock){
        this.concurrentLock = concurrentLock;
    }
    
    @Override
    public void execute(Runnable runnable) {
        try {
            concurrentLock.lockInterruptibly();
            try {
                runnable.run();
            }
            finally {
                concurrentLock.unlock();
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw LockAcquisitionInterruptionException.wrap(e);
        }
    
    }
    
    @Override
    public <T> T call(Callable<T> callable) {
        try {
            concurrentLock.lockInterruptibly();
            try {
                return callable.call();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw LockAcquisitionException.wrap(e);
            } finally {
                concurrentLock.unlock();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw LockAcquisitionException.wrap(e);
        }
    }
    
    @Override
    public LockAcquisition before(Duration duration) {
        if (duration.isZero() || duration.isNegative()) {
            return this;
        }
        return new TimeoutLockAcquisition(this.concurrentLock, duration);
    }
}

class TimeoutLockAcquisition implements LockAcquisition {
    private final java.util.concurrent.locks.Lock concurrentLock;
    private final Duration duration;
    
    TimeoutLockAcquisition(java.util.concurrent.locks.Lock concurrentLock, Duration duration){
        this.concurrentLock = concurrentLock;
        this.duration = duration;
    }
    
    @Override
    public void execute(Runnable runnable) {
        
        try {
            if (!concurrentLock.tryLock(duration.toMillis(), TimeUnit.MILLISECONDS)) {
                throw new LockAcquisitionTimeoutException();
            }
            
            try {
                runnable.run();
            }
            finally {
                concurrentLock.unlock();
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw LockAcquisitionException.wrap(e);
        }
    }
    
    @Override
    public <T> T call(Callable<T> callable) {
        try {
            if (!concurrentLock.tryLock(duration.toMillis(), TimeUnit.MILLISECONDS)) {
                throw new LockAcquisitionTimeoutException();
            }
            
            try {
                return callable.call();
            }catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw LockAcquisitionException.wrap(e);
            } finally {
                concurrentLock.unlock();
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw LockAcquisitionException.wrap(e);
        }
    }
    
    @Override
    public LockAcquisition before(Duration duration) {
        if (duration.isZero() || duration.isNegative()){
            return new MemoryLockAcquisition(this.concurrentLock);
        }
        return new TimeoutLockAcquisition(this.concurrentLock, Comparables.min(this.duration, duration));
    }
}