package org.midheaven.application.translation;

import org.midheaven.culture.Culture;
import org.midheaven.lang.NotNullable;
import org.midheaven.lang.Nullable;

public interface TranslatorsRegistry {
    
    @NotNullable Translator translator(@Nullable Culture culture);
    @NotNullable Translator translator();
}
