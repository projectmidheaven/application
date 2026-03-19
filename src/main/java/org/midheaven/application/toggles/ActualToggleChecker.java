package org.midheaven.application.toggles;

import org.midheaven.application.environment.EnvironmentProperty;
import org.midheaven.application.environment.EnvironmentService;

public class ActualToggleChecker implements ToggleChecker{
    
    private final EnvironmentService environmentService;
    
    public ActualToggleChecker(
        EnvironmentService environmentService
    ){
        this.environmentService = environmentService;
    }
    
    @Override
    public ToggleState check(Toggle toggle) {
        // toggle is checked in levels
        // 1. the toggle itself (CODE)
        if (!toggle.active()){
            return new ActualToggleState(toggle, false);
        }
      
        if (toggle.level().compareTo(ToggleLevel.ENVIRONMENT) >= 0){
            // 2. environment
            if (!environmentService.propertyValue(EnvironmentProperty.ofBoolean("toggle." + toggle.name() + ".active")).orElse(true)){
                return new ActualToggleState(toggle, false);
            };
            
            // 3. persisted state
            if (toggle.level().compareTo(ToggleLevel.STATE) >= 0){
                // TODO
            }
        }
        
        return new ActualToggleState(toggle, true);
    }
}


record ActualToggleState(Toggle toggle, boolean isEnabled) implements ToggleState {

}