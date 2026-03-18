package org.midheaven.application.environment;

import org.midheaven.lang.Maybe;
import org.midheaven.lang.NotNullable;

public interface EnvironmentPropertyValue<T> {
    
    @NotNullable T required();
    @NotNullable Maybe<T> optional();
    
    default boolean isPresent(){
        return optional().isPresent();
    }
    
    default boolean isAbsent(){
        return optional().isAbsent();
    }
    
    default @NotNullable T orElse(@NotNullable T defaultValue){
        return optional().orElse(defaultValue);
    }
  
}
