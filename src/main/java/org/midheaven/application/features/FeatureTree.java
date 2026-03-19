package org.midheaven.application.features;

import org.midheaven.collections.Association;
import org.midheaven.collections.ResizableAssociation;
import org.midheaven.lang.Maybe;

public class FeatureTree {
    
    ResizableAssociation<String, Feature> rootFeatures = Association.builder().resizable().empty();
    ResizableAssociation<String, Feature> resolutionCache = Association.builder().resizable().empty();
    
    protected Feature addRoot(Feature feature){
        if (feature.parent().isPresent()){
            throw new IllegalArgumentException("Root feature cannot have a parent");
        }
        rootFeatures.putValue(feature.path().simpleName(), feature);
        
        return feature;
    }

    public Maybe<Feature> resolve(FeaturePath path){
        if (path == null){
            return Maybe.none();
        }
        
        if (path.parentPath().isAbsent()){
            return rootFeatures.getValue(path.simpleName());
        }
        
        var feature = resolutionCache.getValue(path.qualifiedName());
        
        if (feature.isAbsent()){
            
            feature = path.parentPath().flatMap(this::resolve).map(parent -> {
                
                for (var c : parent.children()){
                    if (c.path().equals(path)){
                        return c;
                    }
                }
                return null;
            });
            
            feature.ifPresent( f -> resolutionCache.putValue(f.path().qualifiedName(), f));
        }
        
        
        return feature;
  
    }
}
