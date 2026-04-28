package org.midheaven.application.translation;

import org.junit.jupiter.api.Test;
import org.midheaven.culture.Culture;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileTranslationRegistryTestCases {
    
    FileTranslatorRegistry registry = new FileTranslatorRegistry("translations", Culture.parse("en-US"));
    
    @Test
    public void resolutionIsConsistent(){
        
        var save = Translatable.from("a");
        
        // all exist in files
        assertEquals("Salvar", registry.translator( Culture.parse("pt-BR")).translate(save).text());
        assertEquals("Guardar", registry.translator( Culture.parse("pt")).translate(save).text());
        assertEquals("Save", registry.translator( Culture.parse("en-US")).translate(save).text());
        assertEquals("Save", registry.translator( Culture.parse("en")).translate(save).text());
        
        // fallback resolution
        
        var delete = Translatable.from("b");
        
        assertEquals("Apagar", registry.translator( Culture.parse("pt-BR")).translate(delete).text());
        assertEquals("Apagar", registry.translator( Culture.parse("pt")).translate(delete).text());
        
        var remove = Translatable.from("c");
        
        assertEquals("Remove", registry.translator( Culture.parse("pt-BR")).translate(remove).text());
        assertEquals("Remove", registry.translator( Culture.parse("pt")).translate(remove).text());
        assertEquals("Remove", registry.translator( Culture.parse("en-US")).translate(remove).text());
        assertEquals("Remove", registry.translator( Culture.parse("en")).translate(remove).text());
    }
}
