package org.midheaven.application.network;

import org.midheaven.lang.NullableEnum;

public enum HttpStatusCategory implements NullableEnum {
    
    UNKNOWN,
    INFORMATION,
    SUCCESS,
    REDIRECTION,
    CLIENT_SIDE_ERROR,
    SERVER_SIDE_ERROR
    ;
    
    public static HttpStatusCategory from(HttpStatus status){
        return from(status.code());
    }
    
    public static HttpStatusCategory from(int code){
        return from(Integer.toString(code));
    }
    
    public static HttpStatusCategory from(String code){
        var c = code.charAt(0);
        return switch (c){
            case '1' -> INFORMATION;
            case '2' -> SUCCESS;
            case '3' -> REDIRECTION;
            case '4' -> CLIENT_SIDE_ERROR;
            case '5' -> SERVER_SIDE_ERROR;
            default -> UNKNOWN;
        };
    }
    @Override
    public boolean isNullEquivalent() {
        return this == UNKNOWN;
    }
    
    public boolean isInformation(){
        return this == INFORMATION;
    }
    
    public boolean isSuccess(){
        return this == SUCCESS;
    }
    
    public boolean isClientSideError(){
        return this == CLIENT_SIDE_ERROR;
    }
    
    public boolean isServerSideError(){
        return this == SERVER_SIDE_ERROR;
    }
    
    public boolean isRedirection(){
        return this == REDIRECTION;
    }
}
