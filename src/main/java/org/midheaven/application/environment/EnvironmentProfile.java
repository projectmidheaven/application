package org.midheaven.application.environment;

public interface EnvironmentProfile {
    
    static EnvironmentProfile named(String name) {
        return new NamedEnvironmentProfile(name);
    }
    
    String name();
}

record NamedEnvironmentProfile(String name) implements EnvironmentProfile{}
