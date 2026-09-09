package org.midheaven.application.contact;

import org.midheaven.culture.CountryCode;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.ParsingException;
import org.midheaven.lang.Strings;
import org.midheaven.validation.Validation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class PostalCodeSpecificationRegistry {
    
    private static final PostalCodeSpecification defaultSpecification =  new DefaultPostalCodeSpecification();
    private static final Map<CountryCode, PostalCodeSpecification> specifications = new ConcurrentHashMap<>();
    
    
    static {
        register(CountryCode.parse("BR"), new BrPostalCodeSpecification());
        register(CountryCode.parse("PT"), new PtPostalCodeSpecification());
    }
    
    public static void register(CountryCode countryCode, PostalCodeSpecification specification){
        specifications.put(countryCode, specification);
    }
    
    private static PostalCodeSpecification specificationFor(CountryCode countryCode){
        var specification =  specifications.get(countryCode);
        if (specification == null){
            return defaultSpecification;
        }
        return specification;
    }
    
    static Validation validate(PostalCode postalCode) {
        if (postalCode == null){
            return Validation.valid();
        }
        return specificationFor(postalCode.countryCode()).validate(postalCode);
    }
    
    static  PostalCode parse(String code, CountryCode countryCode) {
        if (Strings.isBlank(code)){
            return null;
        }
        return specificationFor(countryCode).parse(code, countryCode);
    }
    
    static Maybe<PostalCode> tryParse(String code, CountryCode countryCode) {
        if (Strings.isBlank(code)){
            return Maybe.none();
        }
        
        return specificationFor(countryCode).tryParse(code, countryCode);
    }
}

class DefaultPostalCodeSpecification implements PostalCodeSpecification {
    private static final Strings.Transform NORMALIZATION =  Strings.Transform.create().thenRemoveAllSymbols().thenRaiseCase();
    
    @Override
    public PostalCode parse(String code, CountryCode countryCode) {
        if (NORMALIZATION.apply(code).equals(code)){
            return new PostalCode(countryCode, code);
        }
        throw new ParsingException("Cannot parse fiscal number for country code " + countryCode);
    }
    
    @Override
    public Maybe<PostalCode> tryParse(String code, CountryCode countryCode) {
        return Maybe.of(new PostalCode(countryCode, NORMALIZATION.apply(code)));
    }
    
    @Override
    public Validation validate(PostalCode postalCode) {
        return Validation.valid();
    }
    
}
