package org.midheaven.application;

public interface ConsumingExceptionHandler {
    
    static ConsumingExceptionHandler ignore() {
        return IgnoringConsumingExceptionHandler.NOOP;
    }
    
    void consume(Exception e);
}

class IgnoringConsumingExceptionHandler implements ConsumingExceptionHandler {
    
    static IgnoringConsumingExceptionHandler NOOP = new IgnoringConsumingExceptionHandler();
    
    @Override
    public void consume(Exception e) {
        // no-op
    }
}