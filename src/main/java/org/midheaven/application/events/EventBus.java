package org.midheaven.application.events;

import java.util.Collection;

public interface EventBus {
    
    void send(Event event);
    void sendAll(Collection<? extends Event> events);
    
    EventBus addListener(EventListener listener);
    EventBus addListeners(EventListener ... listeners);
}
