package org.midheaven.application.toggles;

import org.midheaven.collections.Association;
import org.midheaven.collections.ResizableAssociation;
import org.midheaven.lang.Maybe;

class ToggleRegistry {
    
    private static ResizableAssociation<String, Toggle> togglesMapping = Association.builder().resizable().empty();
    
    static Toggle register(Toggle toggle){
        togglesMapping.putValue(toggle.name(), toggle);
        return toggle;
    }
    
    static Maybe<Toggle> resolve(String name){
        return togglesMapping.getValue(name);
    }
}
