package org.midheaven.application.security;

import org.midheaven.collections.DistinctAssortment;
import org.midheaven.collections.ResizableDistinctAssortment;

public class CredentialsBuilder {
    
    ResizableDistinctAssortment<Credential> credentials = DistinctAssortment.builder().resizable().empty();
    
    public CredentialsBuilder addCredential(Credential credential){
        credentials.add(credential);
        return this;
    }
    
    public CredentialsSet build(){
        if (credentials.isEmpty()){
            return EmptyCredentialsSet.INSTANCE;
        }
        return new MapCredentialsSet(credentials);
    }
}
