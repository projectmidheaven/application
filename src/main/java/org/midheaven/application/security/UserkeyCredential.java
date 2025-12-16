package org.midheaven.application.security;

import org.midheaven.keys.Key;
import org.midheaven.lang.HashCode;

public final class UserkeyCredential extends Credential {
    
    public static UserkeyCredential from(Key<?> key){
        return new UserkeyCredential(key);
    }
    
    private final Key<?> key;
    
    private UserkeyCredential(Key<?> key) {
        this.key = key;
    }
    
    public Key<?> key() {
        return key;
    }
    
    @Override
    public boolean equals(Object other){
        return other instanceof UserkeyCredential that
                   && this.key.equals(that.key);
    }
    
    @Override
    public int hashCode(){
        return HashCode.of(this.key);
    }
}
