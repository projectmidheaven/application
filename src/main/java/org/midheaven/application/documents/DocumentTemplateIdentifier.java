package org.midheaven.application.documents;

public interface DocumentTemplateIdentifier {
    
    static DocumentTemplateIdentifier named(String name){
        return new SimpleDocumentTemplateIdentifier(name);
    }
    
}


record SimpleDocumentTemplateIdentifier(String name) implements DocumentTemplateIdentifier{

}