package org.midheaven.application.events;

import org.midheaven.lang.reflection.Mirror;
import org.midheaven.lang.reflection.ParametricTypeReference;
import org.midheaven.lang.reflection.TypeReference;

public abstract class AbstractTypedEventListener<E extends Event> implements EventListener {
    
    ParametricTypeReference<E> type;
    
    protected AbstractTypedEventListener(){
        var typeParameters = Mirror.reflect(this.getClass()).parameterizedType().orElseThrow();
        
        this.type = ParametricTypeReference.from(TypeReference.of(typeParameters.getActualTypeArguments()[0]));
    }
    
    @Override
    public boolean isListening(Event event) {
        return type.isInstance(event);
    }
    
    @Override
    public void onEvent(Event event) {
        if (type.isInstance(event)){
            onTypedEvent(type.cast(event));
        }
    }
    
    protected abstract void onTypedEvent(E event);
}
