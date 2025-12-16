package org.midheaven.application.events;

import java.time.LocalDateTime;

public interface Event {
    
    LocalDateTime occurredAt();
}
