package org.midheaven.application.toggles;

public interface ToggleChecker {
    
    ToggleState check(Toggle toggle);
}
