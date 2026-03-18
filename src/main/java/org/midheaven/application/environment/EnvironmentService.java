package org.midheaven.application.environment;

public interface EnvironmentService {

    <T> EnvironmentPropertyValue<T> propertyValue(EnvironmentProperty<T> property);
    boolean isProfileActive(EnvironmentProfile profile);
    boolean isAnyProfileActive(EnvironmentProfile a, EnvironmentProfile b, EnvironmentProfile ... others);
}
