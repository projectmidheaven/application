package org.midheaven.application.fiscal.br;

import org.midheaven.application.fiscal.FiscalNumber;
import org.midheaven.application.fiscal.FiscalNumberSpecification;
import org.midheaven.application.fiscal.FiscalPersonType;
import org.midheaven.culture.CountryCode;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.ParsingException;
import org.midheaven.lang.Strings;
import org.midheaven.validation.InvalidationReason;
import org.midheaven.validation.Validation;

import java.util.regex.Pattern;

public class BrFiscalNumberSpecification implements FiscalNumberSpecification {
    
    private static final Strings.Transform CFP_NORMALIZATION =  Strings.Transform.create().thenRetainNumericsOnly().thenRaiseCase();
    private static final Strings.Transform CNPJ_NORMALIZATION =  Strings.Transform.create().thenRemoveAllSymbols().thenRaiseCase();
    private static final Strings.Transform TRY_NORMALIZATION =  Strings.Transform.create().thenRemoveAllSymbols().thenRaiseCase();
    
    private static final Pattern DIGITS_ONLY = Pattern.compile("[0-9]+");
    @Override
    public FiscalNumber parse(String code, CountryCode countryCode) {
   
        if (code.length() == 11  && CFP_NORMALIZATION.apply(code).equalsIgnoreCase(code) ) {
            return new CpfFiscalNumber( code);
        } else  if (code.length() == 14 && CNPJ_NORMALIZATION.apply(code).equalsIgnoreCase(code)) {
            return new CnpjFiscalNumber(code);
        }
        throw new ParsingException("Not a possible Brazilian Fiscal Number");
    }
    
    @Override
    public Maybe<FiscalNumber> tryParse(String code, CountryCode countryCode) {
        var normalized = TRY_NORMALIZATION.apply(code);
        if (normalized.length() <= 11){
           return Maybe.of(new CpfFiscalNumber(CFP_NORMALIZATION.apply(normalized)));
        }
        
        return Maybe.of(new CnpjFiscalNumber(CNPJ_NORMALIZATION.apply(normalized)));
    }
    
    @Override
    public Validation validate(FiscalNumber fiscalNumber) {
     
        var length = fiscalNumber.code().length();
        
        if (length != 11 && length != 14){
            return Validation.invalid(InvalidationReason.error("invalid.fiscalNumber.options", "br.fiscalNumber.name", 11, 14));
        } else if (fiscalNumber.code().length() == 11 && !DIGITS_ONLY.matcher(fiscalNumber.code()).matches()){
            return Validation.invalid(InvalidationReason.error("invalid.fiscalNumber.digits", "CPF"));
        }
        
        if (!isValidChecksum(fiscalNumber)) {
            return Validation.invalid(InvalidationReason.error("invalid.fiscalNumber.checksum", length == 11 ? "CPF": "CNPJ"));
        }
        
        return Validation.valid();
    }
    
    
     private boolean isValidChecksum(FiscalNumber fiscalNumber) {
        int[] weights = fiscalNumber.personType() == FiscalPersonType.INDIVIDUAL
            ? new int[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2}
            : new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        
        return checkDigits(equivalentDigits(fiscalNumber), weights);
    }
    
    private boolean checkDigits(int[] digits, int[] weights) {
        if (weights.length + 1 != digits.length) {
            return false;
        }
        
        boolean allEqual = true;
        for (int i = 1; i < digits.length && allEqual; i++) {
            allEqual = allEqual && (digits[i] == digits[i-1]);
        }
        if (allEqual) {
            return false;
        }
        
        int sum = 0;
        for (int i = 0; i < digits.length - 2; i++) {
            sum += digits[i] * weights[i + 1];
        }
        
        int verificationDigit = mod11(sum);
        if (digits[digits.length - 2] != verificationDigit){
            return false;
        }
        
        sum = 0;
        for (int i = 0; i < digits.length - 1; i++) {
            sum += digits[i] * weights[i];
        }
        
        verificationDigit = mod11(sum);
        return digits[digits.length - 1] == verificationDigit;
    }
    
    private int mod11(int sum) {
        int div = sum % 11;
        return div < 2 ? 0 : 11 - div;
    }
    
    private int[] equivalentDigits(FiscalNumber fiscalNumber) {
        int[] digits = new int[fiscalNumber.code().length()];
        int index = 0;
        for (char c : fiscalNumber.code().toCharArray()) {
            if (Character.isDigit(c)) {
                digits[index++] = Character.digit(c, 10);
            } else {
                digits[index++] = c - 48;
            }
        }
        return digits;
    }
}
