package org.midheaven.application.documents;

import org.midheaven.io.ByteContentFormat;
import org.midheaven.io.NamedByteContent;

import java.util.concurrent.CompletableFuture;

public interface DocumentWriter {
    
    
    CompletableFuture<NamedByteContent> write(Document document, ByteContentFormat format);
}
