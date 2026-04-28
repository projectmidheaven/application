package org.midheaven.application.store.tables;

import org.midheaven.lang.Strings;

public class TableCasing {
    
    static String toTableCasing(String text){
        return Strings.transform(text, Strings.Casing.URL, Strings.Casing.LOWER);
    }
}
