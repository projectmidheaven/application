package org.midheaven.application.fiscal;

import org.midheaven.validation.Validation;
import org.midheaven.validation.Validator;

public class FiscalNumberValidator implements Validator<FiscalNumber> {
    
    @Override
    public Validation validate(FiscalNumber fiscalNumber) {
        return FiscalNumberSpecificationRegistry.validate(fiscalNumber);
    }
}
