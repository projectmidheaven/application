package org.midheaven.application.security;

@FunctionalInterface
public interface SubjectAuthorization {
    
    boolean isClearedBy(Subject subject);
}
