package org.midheaven.application.toggles;

import org.midheaven.lang.Maybe;

public interface Toggle {
    
    static ToggleBuilder named(String name) {
        return new ToggleBuilder(name);
    }
    
    static Maybe<Toggle> resolveByName(String name) {
        return ToggleRegistry.resolve(name);
    }
    
    ToggleLevel level();
    String name();
    boolean active();
}


