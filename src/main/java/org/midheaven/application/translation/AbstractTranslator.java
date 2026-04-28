package org.midheaven.application.translation;

import org.midheaven.collections.Sequence;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.NotNullable;
import org.midheaven.lang.Strings;

import java.text.MessageFormat;

public abstract class AbstractTranslator implements Translator{
    
    @Override
    public final Translation translate(Translatable translatable) {
        if (translatable instanceof InvariantTranslatable(String text)){
            return Translation.withText(translatable, text);
        } else if (translatable instanceof ReduceableTranslatable reduceableTranslatable){
            var split = Strings.Splitter.split(reduceableTranslatable.translationKey()).by('.');
            while(!split.isEmpty()){
                var text = resolveText(split.join('.'));
                if (text.isPresent()){
                    return Translation.withText(translatable,applyParameters(text.get(), reduceableTranslatable.translationParameters()));
                }
                
                split = split.withoutFirst();
            }
        }
        
        return Translation.none(translatable);
    }
    

    protected String applyParameters(@NotNullable String parameterizedText, Sequence<Object> parameters){
        if (parameters.isEmpty()){
            return parameterizedText;
        }
        return MessageFormat.format(parameterizedText, parameters.toArray());
    }
    
    protected abstract Maybe<String> resolveText(@NotNullable String key);
}
