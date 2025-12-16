package org.midheaven.application;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.midheaven.application.events.AbstractTypedEventListener;
import org.midheaven.application.events.Event;
import org.midheaven.application.events.ImediateListEventBus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TypedListenerTestCases {
    
    ImediateListEventBus bus = new ImediateListEventBus();
    TestListener listener = new TestListener();
    
    @BeforeAll
    public void setUp(){
        bus.addListener(listener);
    }
    
    @BeforeEach
    public void reset(){
        listener.event = null;
    }
    
    @Test
    public void listeningIsConsistent(){
        var event = new TestEvent();
        
        bus.send(event);
        
        assertNotNull(listener.event);
        assertSame(event, listener.event);
    }
}


class TestEvent implements Event {
    
    @Override
    public LocalDateTime occurredAt() {
        return null;
    }
}

class TestListener extends AbstractTypedEventListener<TestEvent> {
    
    TestEvent event;
    
    @Override
    protected void onTypedEvent(TestEvent event) {
        this.event = event;
    }
}