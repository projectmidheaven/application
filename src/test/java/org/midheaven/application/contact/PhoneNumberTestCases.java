package org.midheaven.application.contact;

import org.junit.jupiter.api.Test;
import org.midheaven.culture.CountryCode;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhoneNumberTestCases {
    
    record Expected(String number, boolean isValid) {}
    
    @Test
    public void testPhoneNumber() {
        Map<String, List<Expected>> map = Map.of(
            "BR", List.of(
                new Expected("+55 (11) 99999-9999", true),
                new Expected("(11) 99999-9999", true),
                new Expected("21 3333-4444", true),
                new Expected("(21) 3333-4444", true),
                new Expected("2133334444", true),
                new Expected("11999999999", true),
                new Expected("999999999", false)
            ),
            "US",List.of(
                new Expected("(415) 555-2671", true)
            )
        );
        
        for (var entry : map.entrySet()) {
            var country = CountryCode.parse(entry.getKey());
            for (var exp : entry.getValue()) {
                var fiscalNumber = PhoneNumber.tryParse(exp.number, country).orElseThrow();
                assertEquals(exp.isValid,  PhoneNumberSpecificationRegistry.isValid(fiscalNumber), country.isoCode() + " number " + exp.number + " is " + (exp.isValid ? "not" : "") +  " valid");
            }
        }
    }
}
