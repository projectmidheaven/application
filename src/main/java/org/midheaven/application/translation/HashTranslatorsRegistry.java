package org.midheaven.application.translation;

import org.midheaven.collections.Association;
import org.midheaven.collections.ResizableAssociation;
import org.midheaven.culture.Culture;

public class HashTranslatorsRegistry implements TranslatorsRegistry  {
    
    private final ResizableAssociation<Culture, Translator> mappings = Association.builder().resizable().empty();
    private Translator defaultTranslator;
    
    public HashTranslatorsRegistry register(Culture culture, Translator translator){
        mappings.putValue(culture, translator);
        return this;
    }
    
    public HashTranslatorsRegistry registerDefault(Translator translator){
        defaultTranslator = translator;
        return this;
    }
    
    @Override
    public Translator translator(Culture culture) {
        return mappings.computeValueIfAbsent(culture, k -> translator());
    }
    
    @Override
    public Translator translator() {
        return defaultTranslator;
    }
}
