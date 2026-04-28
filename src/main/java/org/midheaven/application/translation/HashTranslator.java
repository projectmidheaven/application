package org.midheaven.application.translation;

import org.midheaven.collections.Association;
import org.midheaven.collections.ResizableAssociation;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.NotNullable;

public class HashTranslator extends AbstractTranslator {
    
    private final ResizableAssociation<String, String> mappings = Association.builder().resizable().empty();
    
    public HashTranslator register(String key, String text){
        mappings.putValue(key, text);
        return this;
    }
    
    @Override
    protected Maybe<String> resolveText(@NotNullable String key) {
        return mappings.getValue(key);
    }
}
