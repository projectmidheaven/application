package org.midheaven.application.security;

public interface ProtectedResource {
    
    static ProtectedResource named(String name){
        return new NamedProtectedResource(name);
    }
    String resourceName();
}

record NamedProtectedResource(String resourceName) implements ProtectedResource {}