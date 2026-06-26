package org.midheaven.application.fiscal.br;

import org.midheaven.application.fiscal.FiscalPersonType;

public class CnpjFiscalNumber extends BrFiscalNumber{
    
    protected CnpjFiscalNumber(String code) {
        super(code, FiscalPersonType.COLLECTIVE);
    }
}
