package org.midheaven.application.locks;

import org.midheaven.lang.Check;

public interface Lock {
    
    interface RefinableLock extends Lock {
        default Lock over(Object value) {
            Check.argumentIsNotNull(value, "value");
            return new ScopedLock(name(), value.toString());
        }
    }
    
    static RefinableLock named(String name) {
        return new NamedLock(name);
    }
    
    String name();
    

}


record NamedLock(String name) implements Lock , Lock.RefinableLock {
    
    @Override
    public Lock over(Object value) {
        Check.argumentIsNotNull(value, "value");
        return new ScopedLock(name, value.toString());
    }
}

record ScopedLock(String parentLockName, String scopeValue) implements Lock {
    
    @Override
    public String name() {
        return parentLockName + "::" + scopeValue;
    }
}