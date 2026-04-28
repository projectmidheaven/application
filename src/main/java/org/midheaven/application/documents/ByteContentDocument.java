package org.midheaven.application.documents;

import org.midheaven.collections.Assortment;
import org.midheaven.collections.Sequence;
import org.midheaven.io.ByteContent;
import org.midheaven.io.ByteContentFormat;
import org.midheaven.io.ByteContentFormats;

public class ByteContentDocument implements Document{
    
    public static ByteContentDocument of (ByteContent content){
       return new ByteContentDocument(content);
    }
    
    private final ByteContent content;
    
    public ByteContentDocument(ByteContent content) {
        this.content = content;
    }
    
    @Override
    public Assortment<ByteContentFormat> acceptableFormats() {
        return Sequence.builder().of(content.format());
    }
    
    @Override
    public ByteContent as(ByteContentFormat format) {
        if (!content.format().equals(format)){
            throw new UnsupportedDocumentFormatException(format, ByteContentFormats.TXT);
        }
        return content;
    }
}
