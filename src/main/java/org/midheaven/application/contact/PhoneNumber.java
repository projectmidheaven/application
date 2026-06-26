package org.midheaven.application.contact;

import org.midheaven.culture.CountryCode;
import org.midheaven.lang.HashCode;
import org.midheaven.lang.Maybe;

public class PhoneNumber {
    
    public static PhoneNumber parse(String code, CountryCode countryCode) {
        return PhoneNumberSpecificationRegistry.parse(code, countryCode);
    };
    
    public static Maybe<PhoneNumber> tryParse(String code, CountryCode countryCode) {
        return PhoneNumberSpecificationRegistry.tryParse(code, countryCode);
    };
    
    private final CountryCode countryCode;
    private final String nationalNumber;
    private final String internationalNumber;
    
    protected PhoneNumber(CountryCode countryCode, String nationalNumber, String internationalNumber) {
        this.countryCode = countryCode;
        this.nationalNumber = nationalNumber;
        this.internationalNumber = internationalNumber;
    }
    
    public String nationalNumber(){
        return nationalNumber;
    }
    
    public String internationalNumber(){
        return internationalNumber;
    }
    
    public CountryCode countryCode(){
        return countryCode;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof PhoneNumber other
                   && nationalNumber.equals(other.nationalNumber)
                   && countryCode.equals(other.countryCode);
    }
    
    @Override
    public int hashCode() {
        return HashCode.asymmetric().add(nationalNumber).add(countryCode).hashCode();
    }
    
    @Override
    public String toString() {
        return "(" + countryCode + ")" + nationalNumber;
    }
}
