package org.midheaven.application.toggles;

import org.junit.jupiter.api.Test;
import org.midheaven.application.environment.EnvironmentProperty;
import org.midheaven.application.environment.HashEnvironmentService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToggleCheckerTestCases {
    
    HashEnvironmentService environmentService = new HashEnvironmentService();
    ToggleChecker checker = new ActualToggleChecker(environmentService);
    
    Toggle inactiveToggle = Toggle.named("A").inactive();
    Toggle activeToggle = Toggle.named("B").active();
    
    @Test
    public void checking(){
        
        assertFalse(checker.check(inactiveToggle).isEnabled(), "Inactive toggle check should be disabled");
        assertTrue(checker.check(activeToggle).isEnabled(), "Active toggle check should be enabled");
        
        // disable active via environment
        
        environmentService.putValue(EnvironmentProperty.ofBoolean("toogle.B.active"), false);
        assertTrue(checker.check(activeToggle).isEnabled(), "Active toggle, disabled via environment, check should be disabled");
        
    }
}
