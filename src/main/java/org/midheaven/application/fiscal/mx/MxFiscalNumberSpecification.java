package org.midheaven.application.fiscal.mx;

import org.midheaven.application.fiscal.FiscalNumber;
import org.midheaven.application.fiscal.FiscalNumberSpecification;
import org.midheaven.application.fiscal.FiscalPersonType;
import org.midheaven.culture.CountryCode;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.ParsingException;
import org.midheaven.lang.Strings;
import org.midheaven.validation.InvalidationReason;
import org.midheaven.validation.Validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.regex.Pattern;

public class MxFiscalNumberSpecification implements FiscalNumberSpecification {
    
    // see https://en.wikipedia.org/wiki/Federal_Taxpayer_Registry
    
    private static final Strings.Transform NORMALIZATION =  Strings.Transform.create().thenRemoveAllSymbols().thenRaiseCase();
    private static final Pattern RFC_INDIVIDUAL = Pattern.compile("^[A-ZÑ&]{4}\\d{6}[A-Z0-9]{3}$");
    
    private static final Pattern RFC_COLLECTIVE = Pattern.compile("^[A-ZÑ&]{3}\\d{6}[A-Z0-9]{3}$");
    
    
    @Override
    public FiscalNumber parse(String code, CountryCode countryCode) {
        if (code.length() == 13  && NORMALIZATION.apply(code).equalsIgnoreCase(code) ) {
            return new MxFiscalNumber(code, FiscalPersonType.INDIVIDUAL);
        } else  if (code.length() == 12 && NORMALIZATION.apply(code).equalsIgnoreCase(code)) {
            return new MxFiscalNumber(code, FiscalPersonType.COLLECTIVE);
        }
        throw new ParsingException("Not a possible Mexican Fiscal Number");
    }
    
    @Override
    public Maybe<FiscalNumber> tryParse(String code, CountryCode countryCode) {
        var normalized = NORMALIZATION.apply(code);
        if (normalized.length() <= 12){
            return Maybe.of(new MxFiscalNumber(normalized, FiscalPersonType.COLLECTIVE));
        }
        
        return Maybe.of(new MxFiscalNumber(code, FiscalPersonType.INDIVIDUAL));
    }
    
    @Override
    public Validation validate(FiscalNumber fiscalNumber) {
        var length = fiscalNumber.code().length();
        
        if (length != 12 && length != 13){
            return Validation.invalid(InvalidationReason.error("invalid.fiscalNumber.options", "RFC", 12, 13));
        }
        
        if (!isValidChecksum(fiscalNumber)) {
            return Validation.invalid(InvalidationReason.error("invalid.fiscalNumber.checksum", "RFC"));
        }
        
        return Validation.valid();
    }
    
    private boolean isValidChecksum(FiscalNumber fiscalNumber) {
       if (fiscalNumber.code().length() == 13) {
           return RFC_INDIVIDUAL.matcher(fiscalNumber.code()).matches() && validateDateDigits(fiscalNumber.code().substring(4, 10));
       } else if (fiscalNumber.code().length() == 12) {
          return RFC_COLLECTIVE.matcher(fiscalNumber.code()).matches() && validateDateDigits(fiscalNumber.code().substring(3, 9));
       }
       return false;
    }
    
    private static boolean validateDateDigits(String yymmdd) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuMMdd")
                                              .withResolverStyle(ResolverStyle.STRICT);
            
            LocalDate.parse(yymmdd, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
   
}
