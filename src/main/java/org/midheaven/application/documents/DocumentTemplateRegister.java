package org.midheaven.application.documents;

import org.midheaven.lang.Maybe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DocumentTemplateRegister {
    
    private static Map<DocumentTemplateIdentifier, DocumentTemplate> registry = new ConcurrentHashMap<>();
    
    public static void register(DocumentTemplateIdentifier identifier, DocumentTemplate template){
        registry.put(identifier, template);
    }
    
    public static Maybe<DocumentTemplate> templateFor(DocumentTemplateIdentifier identifier){
        return Maybe.of(registry.get(identifier));
    }
}
