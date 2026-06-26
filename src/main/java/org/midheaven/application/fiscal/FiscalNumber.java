package org.midheaven.application.fiscal;

import org.midheaven.culture.CountryCode;
import org.midheaven.lang.HashCode;
import org.midheaven.lang.Maybe;

public class FiscalNumber {
    
    public static FiscalNumber parse(String code, CountryCode countryCode) {
        return FiscalNumberSpecificationRegistry.parse(code, countryCode);
    };
    
    public static Maybe<FiscalNumber> tryParse(String code, CountryCode countryCode) {
        return FiscalNumberSpecificationRegistry.tryParse(code, countryCode);
    };
    
    private final CountryCode countryCode;
    private final String code;
    private final FiscalPersonType personType;
    
    protected FiscalNumber(CountryCode countryCode, String code, FiscalPersonType personType) {
        this.countryCode = countryCode;
        this.code = code;
        this.personType = personType;
    }
    
    public String code(){
        return code;
    }
    
    public CountryCode countryCode(){
        return countryCode;
    }
    
    public FiscalPersonType personType(){
        return personType;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof FiscalNumber other
            && code.equals(other.code)
            && countryCode.equals(other.countryCode)
            && personType.equals(other.personType);
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
