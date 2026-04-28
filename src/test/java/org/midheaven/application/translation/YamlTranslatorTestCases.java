package org.midheaven.application.translation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class YamlTranslatorTestCases {
    
    YamlTranslator translator = new YamlTranslator("""
a:
  b: Hello, {0}
  c:
    d: World
""");
    
    @Test
    public void canReadPaths (){
        assertEquals("Hello, John",  translator.translate(Translatable.from("a.b", "John")).text());
        assertEquals("World",  translator.translate(Translatable.from("a.c.d")).text());
        
        assertFalse(translator.translate(Translatable.from("xx.c.d")).isTranslated());
        assertFalse(translator.translate(Translatable.from("a.c.xx.yy")).isTranslated());
        assertFalse(translator.translate(Translatable.from("a.c.d.xx.yy")).isTranslated());
    }
}
