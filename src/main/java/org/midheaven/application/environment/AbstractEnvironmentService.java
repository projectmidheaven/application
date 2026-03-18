package org.midheaven.application.environment;

import org.midheaven.lang.Maybe;
import org.midheaven.lang.NotNullable;
import org.midheaven.lang.Nullable;

import java.util.stream.Stream;

public abstract class AbstractEnvironmentService implements EnvironmentService{
  
    @Override
    public boolean isAnyProfileActive(EnvironmentProfile a, EnvironmentProfile b, EnvironmentProfile... others) {
       return isProfileActive(a)
           || isProfileActive(b)
           || (others.length > 0 && Stream.of(others).anyMatch(this::isProfileActive));
    }
    
    @Override
    public <T> EnvironmentPropertyValue<T> propertyValue(EnvironmentProperty<T> property) {
        return new EnvironmentPropertyValue<>(){
            
            @Override
            public T required() {
                return this.optional().orElseThrow(() -> new EnvironmentPropertyValueNotDefinedException(property.name()));
            }
            
            @Override
            public @NotNullable Maybe<T> optional() {
                var raw = readRawValue(property.name());
                if (raw == null){
                    return Maybe.none();
                } else if (property.type().isInstance(raw)){
                    return Maybe.of(property.type().cast(raw));
                } else if (raw instanceof String text){
                    return property.readFrom(text);
                }
                throw new IllegalStateException("Type " + raw.getClass() + " is not compatible with type " + property.type() + " for value property " + property.name());
            }
        };
    }
    
    protected abstract @Nullable Object readRawValue(@NotNullable String name);
}

