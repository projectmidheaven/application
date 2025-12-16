package org.midheaven.application.events;

public interface EventListener {
    
    boolean isListening(Event event);
    void onEvent(Event event);
}
