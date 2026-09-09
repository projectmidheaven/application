package org.midheaven.application.contact;

import org.midheaven.culture.CountryCode;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.ParsingException;
import org.midheaven.lang.Strings;
import org.midheaven.math.Interval;
import org.midheaven.validation.LengthRangeValidator;
import org.midheaven.validation.Validation;

class PtPostalCodeSpecification implements  PostalCodeSpecification {
    private static final Strings.Transform NORMALIZATION =  Strings.Transform.create()
                                                                .thenRetainNumericsOnly();
    
    @Override
    public PostalCode parse(String code, CountryCode countryCode) {
        if (NORMALIZATION.apply(code).equals(code)){
            return new PostalCode(countryCode, code);
        }
        throw new ParsingException("Cannot parse postal code for country code " + countryCode);
    }
    
    @Override
    public Maybe<PostalCode> tryParse(String code, CountryCode countryCode) {
        return Maybe.of(new PostalCode(countryCode, NORMALIZATION.apply(code)));
    }
    
    @Override
    public Validation validate(PostalCode postalCode) {
        return LengthRangeValidator.in(Interval.between(7,7)).validate(postalCode.code());
    }
}
