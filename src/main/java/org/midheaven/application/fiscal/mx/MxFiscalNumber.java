package org.midheaven.application.fiscal.mx;

import org.midheaven.application.fiscal.FiscalNumber;
import org.midheaven.application.fiscal.FiscalPersonType;
import org.midheaven.culture.CountryCode;

public final class MxFiscalNumber extends FiscalNumber {
    
    static final CountryCode MEXICO = CountryCode.parse("MX");
    
    MxFiscalNumber(String code, FiscalPersonType personType) {
        super(MEXICO, code, personType);
    }
}
