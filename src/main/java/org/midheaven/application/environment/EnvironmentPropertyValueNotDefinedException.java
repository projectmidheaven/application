package org.midheaven.application.environment;

public class EnvironmentPropertyValueNotDefinedException extends RuntimeException {
    public EnvironmentPropertyValueNotDefinedException(String name) {
        super("Required environment property `" + name + "` value was not defined");
    }
}
