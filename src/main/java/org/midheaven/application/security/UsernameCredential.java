package org.midheaven.application.security;

import org.midheaven.lang.HashCode;

public final class UsernameCredential extends Credential {
    
    public static UsernameCredential from(String userName){
        return new UsernameCredential(userName);
    }
    
    private final String userName;
    
    private UsernameCredential(String userName) {
        this.userName = userName;
    }
    
    public String userName() {
        return userName;
    }
    
    @Override
    public boolean equals(Object other){
        return other instanceof UsernameCredential that
           && this.userName.equals(that.userName);
    }
    
    @Override
    public int hashCode(){
        return HashCode.of(this.userName);
    }
}
