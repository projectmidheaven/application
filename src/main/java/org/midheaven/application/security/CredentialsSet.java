package org.midheaven.application.security;

import org.midheaven.collections.Array;
import org.midheaven.collections.Association;
import org.midheaven.collections.Assortment;
import org.midheaven.collections.Enumerable;
import org.midheaven.lang.Maybe;

public interface CredentialsSet {
    
    static CredentialsSet of(Credential ... credentials){
        if (credentials.length == 0){
            return EmptyCredentialsSet.INSTANCE;
        }
        return new MapCredentialsSet(Array.of(credentials));
    }
    
    static CredentialsBuilder builder(){
        return new CredentialsBuilder();
    }
    
    boolean isEmpty();
    
    <T extends Credential> Maybe<T> any(Class<T> type);
    <T extends Credential> Enumerable<T> all(Class<T> type);
}

class EmptyCredentialsSet implements CredentialsSet {
    
    static final EmptyCredentialsSet INSTANCE = new EmptyCredentialsSet();
    
    @Override
    public boolean isEmpty() {
        return true;
    }
    
    @Override
    public <T extends Credential> Maybe<T> any(Class<T> type) {
        return Maybe.none();
    }
    
    @Override
    public <T extends Credential> Enumerable<T> all(Class<T> type) {
        return Enumerable.empty();
    }
}

class MapCredentialsSet implements CredentialsSet {
    
    private final Association<String, Enumerable<Credential>> credentialsMapping;
    
    MapCredentialsSet(Assortment<Credential> all){
        credentialsMapping = all.groupBy(it -> it.getClass().getName()).toAssociation();
    }
    
    @Override
    public boolean isEmpty() {
        return credentialsMapping.isEmpty();
    }
    
    @Override
    public <T extends Credential> Maybe<T> any(Class<T> type) {
        return credentialsMapping.getValue(type.getName())
                   .flatMap(Enumerable::first)
                   .map(type::cast);
    }
    
    @Override
    public <T extends Credential> Enumerable<T> all(Class<T> type) {
        return credentialsMapping.getValue(type.getName())
                   .map(all -> all.map(type::cast))
                   .orElseGet(Enumerable::empty);
    }
}