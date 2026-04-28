package org.midheaven.application.features;

import org.midheaven.collections.ResizableSequence;
import org.midheaven.collections.Sequence;
import org.midheaven.lang.Maybe;

public class Feature {
    
    private final String name;
    private final ResizableSequence<Feature> children = Sequence.builder().resizable().empty();
    private Feature parent;
    
    public static Feature named(String name){
        return new Feature(name);
    }
    
    Feature(String name){
        this.name = name;
    }
    
    public Feature add(Feature other){
        if (other.parent != null && other.parent != this){
         throw new IllegalStateException("Feature parent already set to a different parent");
        }
        other.parent = this;
        this.children.add(other);
        
        return this;
    }
    
    public Maybe<Feature> parent(){
        return Maybe.of(parent);
    }
    
    public Sequence<Feature> children(){
        return children;
    }
    
    public FeaturePath path(){
        return new FeaturePath(){
            
            @Override
            public String simpleName() {
                return name;
            }
            
            @Override
            public String qualifiedName() {
                return parent == null ? name : parent.path().qualifiedName() + FeaturePath.SEPARATOR + name;
            }
            
            @Override
            public Maybe<FeaturePath> parentPath() {
                return Maybe.of(parent).map(Feature::path);
            }
        };
    }
}
