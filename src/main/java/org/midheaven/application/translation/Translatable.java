package org.midheaven.application.translation;

import org.midheaven.collections.Sequence;
import org.midheaven.lang.Strings;

public interface Translatable {
    
    static Translatable from(String translationKey){
        return from(translationKey, Sequence.builder().empty());
    }
    
    static Translatable from(String translationKey, Sequence<Object> parameters){
        return new KeyTranslatable(translationKey, parameters);
    }
    
    static Translatable from(String translationKey, Object ... parameters){
        return new KeyTranslatable(translationKey, Sequence.builder().of(parameters));
    }
    
    static Translatable invariant(String text){
        return new InvariantTranslatable(text);
    }
}

record InvariantTranslatable(String text) implements Translatable{

}

record KeyTranslatable(String translationKey,  Sequence<Object> translationParameters) implements ReduceableTranslatable{
    
    @Override
    public boolean canReduce() {
        return true;
    }
    
    @Override
    public ReduceableTranslatable reduce() {
        return new KeyTranslatable(
            Strings.Splitter.split(translationKey).by('.').withoutFirst().join("."),
            translationParameters
        );
    }
}