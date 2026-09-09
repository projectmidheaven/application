package org.midheaven.application.fiscal;

import org.midheaven.culture.CountryCode;
import org.midheaven.math.AvailableRandomGenerators;
import org.midheaven.math.RandomGenerator;

import java.util.function.Function;

public class FiscalNumberRandomGenerator implements RandomGenerator<FiscalNumber> {
    
    public static Function<AvailableRandomGenerators, RandomGenerator<FiscalNumber>> over(CountryCode code, FiscalPersonType type) {
        return (r) -> new FiscalNumberRandomGenerator(code, type, r);
    }
    
    public static Function<AvailableRandomGenerators, RandomGenerator<FiscalNumber>> over(CountryCode code) {
        return over(code, FiscalPersonType.COLLECTIVE);
    }
    
    private final CountryCode code;
    private final AvailableRandomGenerators r;
    private final FiscalPersonType type;
    
    public FiscalNumberRandomGenerator(CountryCode code, FiscalPersonType type, AvailableRandomGenerators r) {
        this.code = code;
        this.r = r;
        this.type = type;
    }
    
    @Override
    public FiscalNumber next() {
        return FiscalNumberSpecificationRegistry.generate(code, type, r);
    }
}
