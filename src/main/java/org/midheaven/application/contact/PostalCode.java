package org.midheaven.application.contact;

import org.midheaven.culture.CountryCode;
import org.midheaven.lang.HashCode;
import org.midheaven.lang.Maybe;

public class PostalCode {
    
    public static PostalCode parse(String code, CountryCode countryCode) {
        return PostalCodeSpecificationRegistry.parse(code, countryCode);
    };
    
    public static Maybe<PostalCode> tryParse(String code, CountryCode countryCode) {
        return PostalCodeSpecificationRegistry.tryParse(code, countryCode);
    };
    
    private final CountryCode countryCode;
    private final String code;
    
    protected PostalCode(CountryCode countryCode, String code) {
        this.countryCode = countryCode;
        this.code = code;
    }
    
    public String code(){
        return code;
    }
    
    public CountryCode countryCode(){
        return countryCode;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof PostalCode other
                   && code.equals(other.code)
                   && countryCode.equals(other.countryCode);
    }
    
    @Override
    public int hashCode() {
        return HashCode.asymmetric().add(code).add(countryCode).hashCode();
    }
    
    @Override
    public String toString() {
        return code + "(" + countryCode + ")";
    }
}
