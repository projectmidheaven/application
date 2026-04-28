package org.midheaven.application.network;

import org.midheaven.lang.Strings;
import org.midheaven.lang.ValueClass;

@ValueClass
public final class Url {

    public static Url parse(String address){
        return Strings.filled(address).map(Url::new).orNull();
    }

    private final String address;

    private Url(String address){
        this.address = address;
    }
    
    public String address(){
        return address;
    }
    
    @Override
    public boolean equals(Object other){
        return other instanceof Url that
            && that.address.equals(this.address);
    }

    @Override
    public int hashCode(){
        return this.address.hashCode();
    }

    @Override
    public String toString(){
        return address;
    }
    
    public Url append(String component){
        if (Strings.isBlank(component)){
            return this;
        }
        if (component.startsWith("/")){
            return new Url(Strings.ensureEndDiffersFrom(this.address, "/").concat(component));
        }
        return new Url(this.address.concat(component));
    }
}
