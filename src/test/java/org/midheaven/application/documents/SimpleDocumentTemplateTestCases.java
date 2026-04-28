package org.midheaven.application.documents;

import org.junit.jupiter.api.Test;
import org.midheaven.io.ByteContentFormats;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleDocumentTemplateTestCases {
    
    @Test
    public void templateWithNoParameters(){
        var body = """

This is a simple template

""";
        var template = SimpleDocumentTemplate.templateFrom(body);
        
        assertEquals(body, template.applyTemplate(new DocumentTemplateParameters()).as(ByteContentFormats.TXT).readAsText());
    }
    
    @Test
    public void templateWithSimpleParameter(){
        var body = """
This is a simple template with a ${name} parameter.
""";
        var template = SimpleDocumentTemplate.templateFrom(body);
        
        assertEquals("""
This is a simple template with a Dolly parameter.
""", template.applyTemplate(new DocumentTemplateParameters().put("name", "Dolly")).as(ByteContentFormats.TXT).readAsText());
    }
    
    @Test
    public void templateWithMultipleParameters(){
        var body = """
Hello, ${name} dear

from the great country of ${country}
we have some $$$

I ${need} to tell you ${goodbye}.
""";
        var template = SimpleDocumentTemplate.templateFrom(body);
        var parameters = new DocumentTemplateParameters()
                             .put("name", "Dolly")
                             .put("country", "Neverland")
                             .put("need", "urge");
            
        assertEquals("""
Hello, Dolly dear

from the great country of Neverland
we have some $$$

I urge to tell you .
""", template.applyTemplate(parameters).as(ByteContentFormats.TXT).readAsText());
    }
}
