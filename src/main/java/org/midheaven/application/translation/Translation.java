package org.midheaven.application.translation;

public class Translation {
    
    static Translation none(Translatable translatable) {
        return new Translation(translatable, null);
    }
    
    static Translation withText(Translatable translatable, String text) {
        return new Translation(translatable, text);
    }
    
    private final Translatable translatable;
    private final String translationText;
    
    private Translation(Translatable translatable, String translationText) {
        this.translatable = translatable;
        this.translationText = translationText;
    }
    
    public boolean isTranslated(){
        return translationText != null;
    }
    
    public String text(){
        if (translationText != null){
            return translationText;
        } else if (translatable instanceof ReduceableTranslatable reduceableTranslatable){
            return '?' + reduceableTranslatable.translationKey() + '?';
        } else if(translatable instanceof InvariantTranslatable(String text)) {
            return text;
        }
        return "?<missing>?";
    }
}
