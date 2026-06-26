package org.midheaven.application.fiscal;

import org.midheaven.application.fiscal.br.BrFiscalNumberSpecification;
import org.midheaven.application.fiscal.pt.PtFiscalNumberSpecification;
import org.midheaven.culture.CountryCode;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.ParsingException;
import org.midheaven.lang.Strings;
import org.midheaven.validation.Validation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FiscalNumberSpecificationRegistry {
    
    private static final FiscalNumberSpecification defaultSpecification =  new DefaultFiscalNumberSpecification();
    private static final Map<CountryCode, FiscalNumberSpecification> specifications = new ConcurrentHashMap<>();
    

    static {
        register(CountryCode.parse("BR"), new BrFiscalNumberSpecification());
        register(CountryCode.parse("PT"), new PtFiscalNumberSpecification());
    }
    
    public static void register(CountryCode countryCode, FiscalNumberSpecification specification){
        specifications.put(countryCode, specification);
    }
    
    private static FiscalNumberSpecification specificationFor(CountryCode countryCode){
         var specification =  specifications.get(countryCode);
         if (specification == null){
             return defaultSpecification;
         }
         return specification;
    }
    
    static Validation validate(FiscalNumber fiscalNumber) {
        if (fiscalNumber == null){
            return Validation.valid();
        }
        return specificationFor(fiscalNumber.countryCode()).validate(fiscalNumber);
    }
    
    static  FiscalNumber parse(String code, CountryCode countryCode) {
        if (Strings.isBlank(code)){
            return null;
        }
        return specificationFor(countryCode).parse(code, countryCode);
    }
    
    static Maybe<FiscalNumber> tryParse(String code, CountryCode countryCode) {
        if (Strings.isBlank(code)){
            return Maybe.none();
        }
        
        return specificationFor(countryCode).tryParse(code, countryCode);
    }
}

class DefaultFiscalNumberSpecification implements FiscalNumberSpecification {
    private static final Strings.Transform NORMALIZATION =  Strings.Transform.create().thenRemoveAllSymbols().thenRaiseCase();
    
    @Override
    public FiscalNumber parse(String code, CountryCode countryCode) {
        if (NORMALIZATION.apply(code).equals(code)){
            return new FiscalNumber(countryCode, code, FiscalPersonType.COLLECTIVE);
        }
        throw new ParsingException("Cannot parse fiscal number for country code " + countryCode);
    }
    
    @Override
    public Maybe<FiscalNumber> tryParse(String code, CountryCode countryCode) {
        return Maybe.of(new FiscalNumber(countryCode, NORMALIZATION.apply(code), FiscalPersonType.COLLECTIVE));
    }
    
    @Override
    public Validation validate(FiscalNumber fiscalNumber) {
        return Validation.valid();
    }
    
}