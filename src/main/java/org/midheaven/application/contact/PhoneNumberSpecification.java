package org.midheaven.application.contact;

import org.midheaven.culture.CountryCode;
import org.midheaven.lang.Maybe;

public interface PhoneNumberSpecification {
    
    PhoneNumber parse(String code, CountryCode countryCode);
    
    Maybe<PhoneNumber> tryParse(String code, CountryCode countryCode);
    
    Boolean isValid(PhoneNumber phoneNumber);
}
