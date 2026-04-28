package org.midheaven.application.documents;

import org.midheaven.io.ByteContent;
import org.midheaven.io.ByteContentFormat;
import org.midheaven.io.ByteContentFormats;

import java.util.ArrayList;
import java.util.List;

public class SimpleDocumentTemplate implements DocumentTemplate {
    
    public static SimpleDocumentTemplate templateFrom(String templateBody){
        return templateFrom(templateBody, ByteContentFormats.TXT);
    }
    
    public static SimpleDocumentTemplate templateFrom(String templateBody, ByteContentFormat format){
        return new SimpleDocumentTemplate(format, templateBody);
    }
    
    private final String templateBody;
    private final ByteContentFormat format;
    private List<TemplatePart> parts;
    
    public SimpleDocumentTemplate(ByteContentFormat format, String templateBody) {
        this.format = format;
        this.templateBody = templateBody;
    }
    
    @Override
    public Document applyTemplate(DocumentTemplateParameters parameters) {
        
        StringBuilder builder = new StringBuilder();
        
        for (var part : parts()){
            part.write(builder, parameters);
        }
        
        return new ByteContentDocument(
            ByteContent.create().withFormat(format).from(builder.toString())
        );
    }
    
    private List<TemplatePart> parts(){
       
        if (parts != null){
            return parts;
        }
        
        parts = new ArrayList<>();
        
        StringBuilder buffer = new StringBuilder();
        int variableStarted = 0;
        for (int i = 0; i < templateBody.length(); i++ ){
            char c = templateBody.charAt(i);
            if (c == '$'){
                if (variableStarted == 1){
                    buffer.append("$"); // previous $
                }
                variableStarted = 1;
            } else if (variableStarted == 1 && c == '{'){
                variableStarted = 2;
                parts.add(new ConstantTemplatePart(buffer.toString()));
                buffer.delete(0, buffer.length());
            } else if (c == '}' && variableStarted == 2){
                variableStarted = 0;
                parts.add(new ParameterTemplatePart(buffer.toString()));
                buffer.delete(0, buffer.length());
            } else {
                if (variableStarted == 1){
                    buffer.append("$"); // previous $
                    variableStarted = 0;
                }
                buffer.append(c);
            }
        }
        parts.add(new ConstantTemplatePart(buffer.toString()));
        
        return parts;
    }
}

interface TemplatePart {
    
    void write(StringBuilder builder, DocumentTemplateParameters parameters);
}

record ConstantTemplatePart(String text) implements TemplatePart {
    
    public void write(StringBuilder builder, DocumentTemplateParameters parameters){
        builder.append(text);
    }
}

record ParameterTemplatePart(String parameterName) implements TemplatePart {
    
    public void write(StringBuilder builder, DocumentTemplateParameters parameters){
        parameters.get(parameterName).ifPresent(builder::append);
    }
}