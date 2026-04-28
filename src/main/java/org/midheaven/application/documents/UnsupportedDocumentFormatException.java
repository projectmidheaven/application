package org.midheaven.application.documents;

import org.midheaven.io.ByteContentFormat;

import java.util.Arrays;

public class UnsupportedDocumentFormatException extends RuntimeException {
    
    public UnsupportedDocumentFormatException(ByteContentFormat requiredFormat, ByteContentFormat ... acceptableFormats) {
        super("Format " + requiredFormat + " is not supported. Accepts only one of : " + Arrays.toString(acceptableFormats));
    }
}
