package org.midheaven.application.environment;

import org.midheaven.lang.Check;
import org.midheaven.lang.NotNullable;
import org.midheaven.lang.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HashEnvironmentService extends AbstractEnvironmentService{
    
    private final Map<String, Object> values = new ConcurrentHashMap<>();
    private final Set<String> activeProfiles = ConcurrentHashMap.newKeySet();
    
    public HashEnvironmentService(){}
    
    public <T> HashEnvironmentService putValue(EnvironmentProperty<T> property, T value){
        values.put(property.name(), value);
        return this;
    }
    
    public <T> HashEnvironmentService putRawValue(EnvironmentProperty<T> property, Object value){
        values.put(property.name(), value);
        return this;
    }
    
    public HashEnvironmentService activate(EnvironmentProfile profile){
        activeProfiles.add(profile.name());
        return this;
    }
    
    public HashEnvironmentService deactivate(EnvironmentProfile profile){
        activeProfiles.remove(profile.name());
        return this;
    }
    
    @Override
    protected @Nullable Object readRawValue(@NotNullable String name) {
        return values.get(name);
    }
    
    @Override
    public boolean isProfileActive(EnvironmentProfile profile) {
        Check.argumentIsNotNull(profile, "profile");
        return activeProfiles.contains(profile.name());
    }
    
}
