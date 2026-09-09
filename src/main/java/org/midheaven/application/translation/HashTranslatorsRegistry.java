package org.midheaven.application.translation;

import org.midheaven.collections.Association;
import org.midheaven.collections.ResizableAssociation;
import org.midheaven.culture.Culture;
import org.midheaven.lang.Check;
import org.midheaven.lang.NotNullable;
import org.midheaven.lang.Nullable;

public class HashTranslatorsRegistry implements TranslatorsRegistry  {
    
    private final ResizableAssociation<Culture, Translator> mappings = Association.builder().resizable().empty();
    private final Translator defaultTranslator;
    
    public HashTranslatorsRegistry(@NotNullable Translator defaultTranslator){
        Check.argumentIsNotNull(defaultTranslator, "defaultTranslator");
        this.defaultTranslator = defaultTranslator;
    }
    
    public HashTranslatorsRegistry register(@NotNullable Culture culture, @NotNullable Translator translator){
        Check.argumentIsNotNull(culture, "culture");
        Check.argumentIsNotNull(translator, "translator");
        mappings.putValue(culture, translator);
        return this;
    }
    
    @Override
    public @NotNullable Translator translator(@Nullable Culture culture) {
        if (culture == null){
            return defaultTranslator;
        }
        return mappings.computeValueIfAbsent(culture, k -> translator());
    }
    
    @Override
    public @NotNullable Translator translator() {
        return defaultTranslator;
    }
}
