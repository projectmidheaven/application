package org.midheaven.application.features;

import org.midheaven.lang.Maybe;
import org.midheaven.lang.Strings;

public abstract class FeaturePath {
    
    static FeaturePath from(String qualifiedName) {
        return new NamedFeaturePath(qualifiedName);
    }
    
    abstract String simpleName();
    abstract String qualifiedName();
    abstract Maybe<FeaturePath> parentPath();
    
    @Override
    public int hashCode() {
        return qualifiedName().hashCode();
    }
    
    @Override
    public boolean equals(Object other) {
        return other instanceof FeaturePath that
            && that.qualifiedName().equals(this.qualifiedName());
    }
    
    @Override
    public String toString() {
        return qualifiedName();
    }
}

class NamedFeaturePath extends FeaturePath{
    
    private final String qualifiedName;
    
    public NamedFeaturePath(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }
    
    @Override
    String simpleName() {
        return Strings.Splitter.split(qualifiedName).by('.').last().orNull();
    }
    
    @Override
    String qualifiedName() {
        return qualifiedName;
    }
    
    @Override
    Maybe<FeaturePath> parentPath() {
        var parent =  Strings.Splitter.split(qualifiedName).by('.').withoutLast();
        if (parent.isEmpty()){
            return Maybe.none();
        }
        return Maybe.of(new NamedFeaturePath(parent.join('.')));
    }
}