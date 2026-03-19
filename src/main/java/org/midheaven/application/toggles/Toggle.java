package org.midheaven.application.toggles;

public interface Toggle {
    
    static ToggleBuilder named(String name) {
        return new ToggleBuilder(name);
    }
    
    ToggleLevel level();
    String name();
    boolean active();
}


