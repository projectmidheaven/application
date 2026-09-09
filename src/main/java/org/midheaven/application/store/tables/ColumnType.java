package org.midheaven.application.store.tables;

public enum ColumnType {
    
    IDENTIFIER, // Long
    FOREIGN_IDENTIFIER, // long
    TEXT, // varchar
    MEMO, // varchar
    COUNT, // integer
    DATE, // date
    TIME, // time
    DATETIME, // date and time UTC
    LOGICAL, // boolean
    CHOICE, // enum
    NUMERIC, //  BigDecimal
    ;
    
    public boolean isIdentifier(){
        return switch (this){
            case IDENTIFIER,FOREIGN_IDENTIFIER -> true;
            case COUNT,TEXT, MEMO , DATE, TIME, DATETIME, LOGICAL, CHOICE, NUMERIC-> false;
        };
    }
    
    public boolean isTextual(){
        return switch (this){
            case TEXT, MEMO -> true;
            case COUNT, IDENTIFIER,FOREIGN_IDENTIFIER, DATE, TIME, DATETIME, LOGICAL, CHOICE, NUMERIC-> false;
        };
    }
    public boolean isTemporal(){
        return switch (this){
            case DATE , DATETIME , TIME -> true;
            case TEXT, MEMO , COUNT, IDENTIFIER, FOREIGN_IDENTIFIER, LOGICAL, CHOICE, NUMERIC-> false;
        };
    }
    
}
