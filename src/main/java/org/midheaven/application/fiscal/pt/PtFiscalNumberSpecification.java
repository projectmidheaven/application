package org.midheaven.application.fiscal.pt;

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

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PtFiscalNumberSpecification implements FiscalNumberSpecification {
    
    // from https://pt.wikipedia.org/wiki/N%C3%BAmero_de_identifica%C3%A7%C3%A3o_fiscal
    
    private static final Strings.Transform NORMALIZATION =  Strings.Transform.create().thenRetainNumericsOnly().thenRaiseCase();
    private static final Set<String> COLLECTIVE_PREFIXES = Set.of("5", "6", "7", "8", "9");
    private static final Set<String> INDIVIDUAL_PREFIXES = Set.of("1", "2", "3" , "4");
    
    private static final Pattern DIGITS_ONLY = Pattern.compile("[0-9]+");
    private static final int[] WEIGHTS = new int[]{9, 8, 7, 6, 5, 4, 3, 2};
    
    @Override
    public FiscalNumber parse(String code, CountryCode countryCode) {
        if (COLLECTIVE_PREFIXES.contains(code.substring(0 , 1)) && NORMALIZATION.apply(code).equalsIgnoreCase(code)) {
            return new PtFiscalNumber(code, FiscalPersonType.COLLECTIVE);
        } else if (INDIVIDUAL_PREFIXES.contains(code.substring(0 , 1))  && NORMALIZATION.apply(code).equalsIgnoreCase(code) ) {
            return new PtFiscalNumber( code, FiscalPersonType.INDIVIDUAL);
        }
        throw new ParsingException("Not a possible Portuguese Fiscal Number");
    }
    
    @Override
    public Maybe<FiscalNumber> tryParse(String code, CountryCode countryCode) {
        var normalized = NORMALIZATION.apply(code);
        if ( COLLECTIVE_PREFIXES.contains(code.substring(0 , 1)) ) {
            return Maybe.of(new PtFiscalNumber(code, FiscalPersonType.COLLECTIVE));
        }
        
        return Maybe.of(new PtFiscalNumber( code, FiscalPersonType.INDIVIDUAL));
    }
    
    @Override
    public Validation validate(FiscalNumber fiscalNumber) {
        var length = fiscalNumber.code().length();
        
        if (length != 9){
            return Validation.invalid(InvalidationReason.error("invalid.fiscalNumber.options", "NIF", 9));
        }
        
        if (!DIGITS_ONLY.matcher(fiscalNumber.code()).matches()){
            return Validation.invalid(InvalidationReason.error("invalid.fiscalNumber.digits", "NIF"));
        }
        
        if (!isValidChecksum(fiscalNumber)) {
            return Validation.invalid(InvalidationReason.error("invalid.fiscalNumber.checksum", "NIF"));
        }
        
        return Validation.valid();
    }
    
    @Override
    public FiscalNumber generate(CountryCode countryCode, FiscalPersonType type, AvailableRandomGenerators randomGenerators) {
        
        int[] digits = randomGenerators.integers().between(0,9).stream().limit(8).mapToInt(i -> i).toArray();
        
        if (type == FiscalPersonType.COLLECTIVE){
            digits[0] = 5;
        } else {
            digits[0] = 1;
        }
        return new PtFiscalNumber(Arrays.stream(digits)
                                      .mapToObj(Integer::toString)
                                      .collect(Collectors.joining()) + verificationDigit(digits), type);
    }
    
    private boolean isValidChecksum(FiscalNumber fiscalNumber) {
  
        var digits = equivalentDigits(fiscalNumber.code());
        if (digits[0] == 8){
            return false; // discontinued
        }
        return checkDigits(digits);
    }
    
    private int verificationDigit(int[] digits){
        int sum = 0;
        for (int i = 0; i < WEIGHTS.length - 1; i++) {
            sum += digits[i] * WEIGHTS[i];
        }
        
        return mod11(sum);
    }
    
    private boolean checkDigits(int[] digits) {
        if (WEIGHTS.length + 1 != digits.length) {
            return false;
        }
        
        boolean allEqual = true;
        for (int i = 1; i < digits.length && allEqual; i++) {
            allEqual = allEqual && (digits[i] == digits[i-1]);
        }
        
        if (allEqual) {
            return false;
        }
        
        return digits[digits.length - 1] == verificationDigit(digits);
    }
    
    private int mod11(int sum) {
        int div = sum % 11;
        if (div < 2){
            return 0;
        }
        return 11 - div;
    }
    
    private int[] equivalentDigits(String fiscalNumberCode) {
        int[] digits = new int[fiscalNumberCode.length()];
        int index = 0;
        for (char c : fiscalNumberCode.toCharArray()) {
            digits[index++] = Character.digit(c, 10);
        }
        return digits;
    }
}
