package org.midheaven.application.fiscal.mx;

import org.midheaven.application.fiscal.FiscalNumber;
import org.midheaven.application.fiscal.FiscalNumberSpecification;
import org.midheaven.application.fiscal.FiscalPersonType;
import org.midheaven.culture.CountryCode;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.ParsingException;
import org.midheaven.lang.Strings;
import org.midheaven.math.AvailableRandomGenerators;
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
    
    // Official SAT index dictionary mapping alphanumeric characters to positional values
    private static final String SAT_DICTIONARY = "0123456789ABCDEFGHIJKLMN&OPQRSTUVWXYZ Ñ";
    
    
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
    
    @Override
    public FiscalNumber generate(CountryCode countryCode, FiscalPersonType type, AvailableRandomGenerators randomGenerators) {
        var head = randomGenerators.strings().withLength(type == FiscalPersonType.COLLECTIVE ? 3 : 4).alphabetic().next();
        var tail = randomGenerators.strings().withLength(2).alphanumeric().next();
        var day = randomGenerators.integers().between(10,31).next();
        var month = randomGenerators.integers().between(10,12).next();
        
        var code = (head + "20" + month + day + tail).toUpperCase();
        return new MxFiscalNumber( code + verificationDigit(code + "0"), type);
    }
    
    private boolean isValidChecksum(FiscalNumber fiscalNumber) {
       if (fiscalNumber.code().length() == 13) {
           return RFC_INDIVIDUAL.matcher(fiscalNumber.code()).matches() && validateDateDigits(fiscalNumber.code().substring(4, 10));
       } else if (fiscalNumber.code().length() == 12) {
          return RFC_COLLECTIVE.matcher(fiscalNumber.code()).matches() && validateDateDigits(fiscalNumber.code().substring(3, 9));
       }
       
       var code = fiscalNumber.code();
       return code.charAt(code.length() - 1) == verificationDigit(code);
    }
    
    private char verificationDigit(String rfc){
        String rfcWithoutCheckDigit = rfc.substring(0, rfc.length() - 1);
        
        // Core Modulo 11 math calculation
        int len = rfcWithoutCheckDigit.length();
        int sum = (len == 11) ? 0 : 481; // Adjustment base factor if it's a Persona Moral (12 chars total)
        int initialWeight = len + 1;
        
        for (int i = 0; i < len; i++) {
            char c = rfcWithoutCheckDigit.charAt(i);
            int charValue = SAT_DICTIONARY.indexOf(c);
            
            if (charValue == -1) {
                return 0; // Character not allowed in dictionary
            }
            
            sum += charValue * (initialWeight - i);
        }
        
        int remainder = sum % 11;
       
        if (remainder == 0) {
            return '0';
        } else if (remainder == 1) {
            return 'A'; // Special rule: 10 returns 'A'
        } else {
            int calculatedValue = 11 - remainder;
            return Integer.toString(calculatedValue).charAt(0);
        }
        
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
