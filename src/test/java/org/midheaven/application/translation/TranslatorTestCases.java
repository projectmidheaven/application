package org.midheaven.application.translation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TranslatorTestCases {
    
    Translator translator = new HashTranslator()
            .register("messages.text", "Hello")
            .register("messages.name", "Hello, {0}");
        ;
        
   TranslatorsRegistry translatorsRegistry = new HashTranslatorsRegistry()
           .registerDefault(translator);
    
    @Test
    public void invariantReturnsItsText(){
        var translation = translatorsRegistry.translator().translate(Translatable.invariant("Hello"));
        
        assertTrue(translation.isTranslated());
        assertEquals("Hello", translation.text());
    }
    
    @Test
    public void keyReturnsMappedText(){
        var translation = translatorsRegistry.translator().translate(Translatable.from("messages.text"));
        
        assertTrue(translation.isTranslated());
        assertEquals("Hello", translation.text());
    }
    
    @Test
    public void parametersAreApplied(){
        var translation = translatorsRegistry.translator().translate(Translatable.from("messages.name", "Dear"));
        
        assertTrue(translation.isTranslated());
        assertEquals("Hello, Dear", translation.text());
    }
}
