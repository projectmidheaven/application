package org.midheaven.application.store.tables;

public enum ColumnType {
    
    IDENTIFIER, // Long
    TEXT, // varchar
    MEMO, // varchar
    COUNT, // integer
    DATE, // date
    TIME, // time
    DATETIME, // date and time UTC
    LOGICAL, // boolean
    CHOICE, // enum
    NUMERIC, //  BigDecimal
    SECRET, //  2-way encryption
    SECRET_HASH //  1-way encryption
    ;
    public boolean isTextual(){
        return switch (this){
            case TEXT, MEMO, SECRET_HASH -> true;
            case COUNT, IDENTIFIER, DATE, TIME, DATETIME, LOGICAL, CHOICE, NUMERIC, SECRET-> false;
        };
    }
}
