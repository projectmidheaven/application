package org.midheaven.application.contact;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.midheaven.culture.CountryCode;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.ParsingException;
import org.midheaven.lang.Strings;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PhoneNumberSpecificationRegistry {
    
    private static final PhoneNumberSpecification defaultSpecification =  new DefaultPhoneNumberSpecification();
    private static final Map<CountryCode, PhoneNumberSpecification> specifications = new ConcurrentHashMap<>();
    
    public static void register(CountryCode countryCode, PhoneNumberSpecification specification){
        specifications.put(countryCode, specification);
    }
    
    private static PhoneNumberSpecification specificationFor(CountryCode countryCode){
        var specification =  specifications.get(countryCode);
        if (specification == null){
            return defaultSpecification;
        }
        return specification;
    }
    
    static PhoneNumber parse(String code, CountryCode countryCode) {
        if (Strings.isBlank(code) || countryCode == null){
            return null;
        }
        return specificationFor(countryCode).parse(code, countryCode);
    }
    
    static Maybe<PhoneNumber> tryParse(String code, CountryCode countryCode) {
        if (Strings.isBlank(code) || countryCode == null){
            return Maybe.none();
        }
        return specificationFor(countryCode).tryParse(code, countryCode);
    }
    
    static Boolean isValid(PhoneNumber  phoneNumber) {
        if (phoneNumber == null){
            return null;
        }
        return specificationFor(phoneNumber.countryCode()).isValid(phoneNumber);
    }
    
}


class DefaultPhoneNumberSpecification implements PhoneNumberSpecification {
  
    @Override
    public PhoneNumber parse(String code, CountryCode countryCode) {
        
     
        try {
            return numberOf(code,countryCode);
        } catch (NumberParseException e) {
            throw new ParsingException(e.getMessage());
        }
    }
    
    @Override
    public Maybe<PhoneNumber> tryParse(String code, CountryCode countryCode) {
        try {
           return Maybe.of(numberOf(code,countryCode));
        } catch (NumberParseException e) {
            return Maybe.none();
        }
    }
    
    private PhoneNumber numberOf(String code, CountryCode countryCode) throws NumberParseException {
        var util = PhoneNumberUtil.getInstance();
        var numberProto = util.parse(code, countryCode.isoCode());
        
        return new PhoneNumber(countryCode,
            Long.toString(numberProto.getNationalNumber()),
            util.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.E164)
        );
    }
    
    @Override
    public Boolean isValid(PhoneNumber phoneNumber) {
        var util = PhoneNumberUtil.getInstance();
        try {
            return util.isValidNumberForRegion(util.parse(phoneNumber.nationalNumber(), phoneNumber.countryCode().isoCode()), phoneNumber.countryCode().isoCode());
        } catch (NumberParseException e) {
            return false;
        }
    }
}