package org.midheaven.application.network;

import org.midheaven.lang.Strings;
import org.midheaven.lang.ValueClass;

@ValueClass
public final class Email  {

    public static Email parse(String address){
        return Strings.filled(address).map(Email::new).orNull();
    }

    private final String address;

    private Email(String address){
        this.address = address.toLowerCase();
    }

    @Override
    public boolean equals(Object other){
        return other instanceof Email that
            && that.address.equals(this.address);
    }

    public String address(){
        return address;
    }
    
    @Override
    public int hashCode(){
        return this.address.hashCode();
    }

    @Override
    public String toString(){
        return address;
    }
}
