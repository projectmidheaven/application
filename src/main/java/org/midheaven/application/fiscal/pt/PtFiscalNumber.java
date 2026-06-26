package org.midheaven.application.fiscal.pt;

import org.midheaven.application.fiscal.FiscalNumber;
import org.midheaven.application.fiscal.FiscalPersonType;
import org.midheaven.culture.CountryCode;

public final class PtFiscalNumber extends FiscalNumber {
    
    static final CountryCode PORTUGAL = CountryCode.parse("PT");
    
    PtFiscalNumber(String code, FiscalPersonType personType) {
        super(PORTUGAL, code, personType);
    }
}
