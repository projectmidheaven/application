package org.midheaven.application.fiscal;

import org.midheaven.culture.CountryCode;
import org.midheaven.lang.Maybe;
import org.midheaven.math.AvailableRandomGenerators;
import org.midheaven.validation.Validation;

public interface FiscalNumberSpecification {
    
    FiscalNumber parse(String code, CountryCode countryCode);
    
    Maybe<FiscalNumber> tryParse(String code, CountryCode countryCode);
    
    Validation validate(FiscalNumber fiscalNumber);
    
    FiscalNumber generate(CountryCode countryCode,FiscalPersonType type, AvailableRandomGenerators randomGenerators);
}
