package org.midheaven.application.events;

import java.time.LocalDateTime;

public abstract class AbstractEvent implements Event {
    
    private final LocalDateTime occurredAt;
    
    protected AbstractEvent(LocalDateTime occurredAt){
        this.occurredAt = occurredAt;
    }
    
    @Override
    public final LocalDateTime occurredAt() {
        return occurredAt;
    }
}
