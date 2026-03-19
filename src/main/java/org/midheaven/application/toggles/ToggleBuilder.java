package org.midheaven.application.toggles;

public class ToggleBuilder {
    
    private final String name;
    private ToggleLevel level = ToggleLevel.STATE;
    
    public ToggleBuilder(String name) {
      this.name = name;
    }
    
    public ToggleBuilder level(ToggleLevel level){
        this.level = level;
        return this;
    }
    
    public Toggle active(){
        return new BuildToggle(name, level, true);
    }
    
    public Toggle inactive(){
        return new BuildToggle(name, level, false);
    }
}

record BuildToggle(String name, ToggleLevel level, boolean active) implements Toggle {

}