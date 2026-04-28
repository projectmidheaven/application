package org.midheaven.application.translation;

import org.midheaven.collections.Sequence;

public interface ReduceableTranslatable extends Translatable {
    
    String translationKey();
    Sequence<Object> translationParameters();
    
    boolean canReduce();
    ReduceableTranslatable reduce();
    
}
