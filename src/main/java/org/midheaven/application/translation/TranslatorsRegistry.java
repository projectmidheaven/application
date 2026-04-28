package org.midheaven.application.translation;

import org.midheaven.culture.Culture;

public interface TranslatorsRegistry {
    
    Translator translator(Culture culture);
    Translator translator();
}
