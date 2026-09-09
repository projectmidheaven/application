package org.midheaven.application.events;

import java.util.Collection;

public interface EventBus {
    
    void send(Event event);
    void sendAll(Collection<? extends Event> events);
    
    EventBus addListener(EventListener listener);
    
    default EventBus addListeners(Collection<? extends EventListener> listeners){
      for (var listener : listeners){
          addListener(listener);
      }
      return this;
    }
    
    default EventBus addListeners(EventListener ... listeners){
        for (var listener : listeners){
            addListener(listener);
        }
        return this;
    }
}
