package org.midheaven.application.events;

import org.midheaven.application.ConsumingExceptionHandler;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ImediateListEventBus implements EventBus{
    
    private final List<EventListener> listeners = new CopyOnWriteArrayList<>();
    private final ConsumingExceptionHandler handler;
    
    public ImediateListEventBus(){
        this(ConsumingExceptionHandler.ignore());
    }
    
    public ImediateListEventBus(ConsumingExceptionHandler handler){
        this.handler = handler;
    }
    
    public ImediateListEventBus addListener(EventListener eventListener){
        this.listeners.add(eventListener);
        return this;
    }
    
    @Override
    public void send(Event event) {
        for (var listener : listeners){
            try {
                if (listener.isListening(event)){
                    listener.onEvent(event);
                }
            }catch (Exception e){
                handler.consume(e);
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
