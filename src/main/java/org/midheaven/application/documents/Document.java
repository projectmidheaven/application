package org.midheaven.application.documents;

import org.midheaven.collections.Assortment;
import org.midheaven.io.ByteContent;
import org.midheaven.io.ByteContentFormat;

public interface Document {
    
    Assortment<ByteContentFormat> acceptableFormats();
    ByteContent as(ByteContentFormat format);
}
