package org.midheaven.application.environment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnvironmentServiceTestCases {
    
    static final EnvironmentProperty<String> A = EnvironmentProperty.ofString("test.a");
    static final EnvironmentProperty<Boolean> B = EnvironmentProperty.ofBoolean("test.b");
    static final EnvironmentProperty<Duration> C = EnvironmentProperty.ofDuration("test.c", ChronoUnit.HOURS);
    
    static final EnvironmentProfile PROFILE_A = EnvironmentProfile.named("A");
    static final EnvironmentProfile PROFILE_B = EnvironmentProfile.named("B");
    
    EnvironmentService service = new HashEnvironmentService()
             .putValue(A, "Hello")
             .putValue(B, true)
             .putRawValue(C, "10")
             .activate(PROFILE_A)
        ;
    
    @Test
    public void testValue(){
        
        var valueA = service.propertyValue(A);
        assertTrue(valueA.isPresent());
        assertFalse(valueA.isAbsent());
        assertEquals("Hello", valueA.required());
        assertEquals("Hello", valueA.optional().get());
        
        var valueB = service.propertyValue(B);
        assertTrue(valueB.isPresent());
        assertFalse(valueB.isAbsent());
        assertTrue(valueB.required());
        assertTrue(valueB.optional().get());
        
        var valueC = service.propertyValue(C);
        assertTrue(valueC.isPresent());
        assertFalse(valueC.isAbsent());
        assertEquals(Duration.ofHours(10), valueC.required());
        assertEquals(Duration.ofHours(10), valueC.optional().get());
    }
    
    @Test
    public void testProfiles(){
        service.isProfileActive(PROFILE_A);
    }
    
}
