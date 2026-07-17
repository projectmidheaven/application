package org.midheaven.application.security;

public class AuthorizationFailedException extends RuntimeException {
    
    public AuthorizationFailedException(){}
    
    protected AuthorizationFailedException(String message){
       super(message);
    }
}
