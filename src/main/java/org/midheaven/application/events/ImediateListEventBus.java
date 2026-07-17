package org.midheaven.application.events;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ImediateListEventBus implements EventBus{
    
    private final List<EventListener> listeners = new CopyOnWriteArrayList<>();
    
    public ImediateListEventBus addListener(EventListener eventListener){
        this.listeners.add(eventListener);
        return this;
    }
    
    public ImediateListEventBus addListeners(EventListener ... eventListeners){
        this.listeners.addAll(Arrays.asList(eventListeners));
        return this;
    }
    
    @Override
    public void send(Event event) {
        for (var listener : listeners){
            if (listener.isListening(event)){
                listener.onEvent(event);
            }
        }
    }
    
    @Override
    public void sendAll(Collection<? extends Event> events) {
        for (var event : events){
            send(event);
        }
    }
}
