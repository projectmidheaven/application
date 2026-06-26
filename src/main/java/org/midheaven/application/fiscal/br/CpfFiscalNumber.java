package org.midheaven.application.fiscal.br;

import org.midheaven.application.fiscal.FiscalPersonType;

public class CpfFiscalNumber extends BrFiscalNumber{
    
    protected CpfFiscalNumber(String code) {
        super(code, FiscalPersonType.INDIVIDUAL);
    }
}
