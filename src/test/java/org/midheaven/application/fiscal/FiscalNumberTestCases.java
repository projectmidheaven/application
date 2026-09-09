package org.midheaven.application.fiscal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.midheaven.application.fiscal.mx.MxFiscalNumberSpecification;
import org.midheaven.culture.CountryCode;
import org.midheaven.math.RandomGeneratorProvider;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FiscalNumberTestCases {
    
    record Expected(String code, boolean isValid, FiscalPersonType type) {}
    
    @BeforeAll
    public void setup() {
        FiscalNumberSpecificationRegistry.register(CountryCode.parse("MX"), new MxFiscalNumberSpecification());
    }
    
    @Test
    public void testValidation() {
        
        Map<String, List<Expected>> map = Map.of(
            "MX",  List.of(
                new Expected("GAAA750430HZ0", true, FiscalPersonType.INDIVIDUAL),
                new Expected("AAGB860519G31", true, FiscalPersonType.INDIVIDUAL),
                new Expected("ACZ-970422-FSO", true,FiscalPersonType.COLLECTIVE),
                new Expected("TCA-940217-MT7", true, FiscalPersonType.COLLECTIVE),
                new Expected("PEPJ8001019Q8", true, FiscalPersonType.INDIVIDUAL),
                new Expected("XXXX000000XXX", false, FiscalPersonType.INDIVIDUAL)
            ),
            "BR", List.of(
                new Expected("43.318.222/0001-60", true, FiscalPersonType.COLLECTIVE),
                new Expected("SH.JC8.4HD/0001-02", true, FiscalPersonType.COLLECTIVE),
                new Expected("11.111.111/1111-11", false, FiscalPersonType.COLLECTIVE),
                new Expected("33.870.360/0001-73", false, FiscalPersonType.COLLECTIVE),
                new Expected("12.345.678/0001-99", false, FiscalPersonType.COLLECTIVE),
                new Expected("242.740.841-75", true, FiscalPersonType.INDIVIDUAL),
                new Expected("111.111.111-11", false, FiscalPersonType.INDIVIDUAL),
                new Expected("242.740.841-73", false, FiscalPersonType.INDIVIDUAL),
                new Expected("132.576.085-14", false, FiscalPersonType.INDIVIDUAL),
                new Expected("242740841", false, FiscalPersonType.INDIVIDUAL),
                new Expected("XPO740841", false, FiscalPersonType.INDIVIDUAL)
            ),
            "PT",List.of(
                new Expected("501442600", true,  FiscalPersonType.COLLECTIVE),
                new Expected("719209377", true,  FiscalPersonType.COLLECTIVE),
                new Expected("501442601", false, FiscalPersonType.COLLECTIVE),
                new Expected("999999999", false, FiscalPersonType.COLLECTIVE),
                new Expected("111111111", false, FiscalPersonType.INDIVIDUAL),
                new Expected("5014A2600", false, FiscalPersonType.COLLECTIVE)
            )
        );
        
        for (var entry : map.entrySet()) {
            var country = CountryCode.parse(entry.getKey());
            for (var exp : entry.getValue()) {
                var fiscalNumber = FiscalNumber.tryParse(exp.code, country).orElseThrow();
                var validation = FiscalNumberSpecificationRegistry.validate(fiscalNumber);
                assertEquals(exp.isValid,  validation.isValid(), country.isoCode() + " code " + exp.code + " is " + (exp.isValid ? "not" : "") +  " valid" + validation.reasons() );
                assertEquals(exp.type, fiscalNumber.personType(), country.isoCode() + " code " + exp.code + " is not of expected type " + exp.type);
            }
        }
        
    }
    
    @Test
    public void testRandomGeneration() {
        var validator = new FiscalNumberValidator();
        var random = RandomGeneratorProvider.seedable(1);
        var supportedCountries = List.of("BR", "PT", "MX");
        for (var code : supportedCountries){
            for (var type : FiscalPersonType.values()){
                var fiscalNumber = random.provide(FiscalNumberRandomGenerator.over(CountryCode.parse(code), type)).next();
                
                var validation = validator.validate(fiscalNumber);
                
                assertTrue(validation.isValid(), "Invalid generated fiscal number for country " + code + " and type " + type + "." + validation);
            }
        }
      

    }
}
