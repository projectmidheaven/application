package org.midheaven.application.security;

import org.midheaven.lang.HashCode;

import java.util.Arrays;

public final class PasswordCredential extends Credential {
    
    public static PasswordCredential from(byte[] content){
        return new PasswordCredential(content);
    }
    
    private final byte[] content;
    
    private PasswordCredential(byte[] content) {
        this.content = content;
    }
    
    public byte[] content(){
        return content;
    }
    
    @Override
    public boolean equals(Object other){
        return other instanceof PasswordCredential pass
            && Arrays.equals(this.content, pass.content);
    }
    
    @Override
    public int hashCode(){
        return HashCode.of(this.content);
    }
}
