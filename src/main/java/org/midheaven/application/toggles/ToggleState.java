package org.midheaven.application.toggles;

public interface ToggleState {

    Toggle toggle();
    boolean isEnabled();
}