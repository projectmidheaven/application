package org.midheaven.application.toggles;

import org.midheaven.math.Interval;

import java.time.LocalDate;

public interface ToggleSettings {
    
    String name();
    boolean isEnabled();
    Interval<LocalDate> term();
}
