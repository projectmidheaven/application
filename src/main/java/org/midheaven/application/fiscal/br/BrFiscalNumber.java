package org.midheaven.application.fiscal.br;

import org.midheaven.application.fiscal.FiscalNumber;
import org.midheaven.application.fiscal.FiscalPersonType;
import org.midheaven.culture.CountryCode;

public abstract class BrFiscalNumber extends FiscalNumber {
    
    static final CountryCode BRAZIL = CountryCode.parse("BR");
    
    protected BrFiscalNumber(String code, FiscalPersonType personType) {
        super(BRAZIL, code, personType);
    }
}
