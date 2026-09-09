package org.midheaven.application.contact;

import org.midheaven.culture.CountryCode;
import org.midheaven.lang.Maybe;
import org.midheaven.validation.Validation;

public interface PostalCodeSpecification {
    
    PostalCode parse(String code, CountryCode countryCode);
    
    Maybe<PostalCode> tryParse(String code, CountryCode countryCode);
    
    Validation validate(PostalCode postalCode);
}
