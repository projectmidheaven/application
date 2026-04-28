package org.midheaven.application.documents;

import org.midheaven.collections.Association;
import org.midheaven.collections.ResizableAssociation;
import org.midheaven.lang.Maybe;

public final class DocumentTemplateParameters {
    
    public static DocumentTemplateParameters empty(){
        return new DocumentTemplateParameters();
    }
    
    private ResizableAssociation<String, Object> mapping = Association.builder().resizable().empty();
    
    public DocumentTemplateParameters put(String name, Object value){
        mapping.putValue(name, value);
        return this;
    }
    
    public boolean isEmpty(){
        return mapping.isEmpty();
    }
    
    public Maybe<Object> get(String name){
        return mapping.getValue(name);
    }
    
    public <T> Maybe<T> getAs(String name, Class<T> type){
        return mapping.getValue(name).ofType(type);
    }
}
