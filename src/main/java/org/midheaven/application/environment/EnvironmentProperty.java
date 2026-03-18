package org.midheaven.application.environment;

import org.midheaven.lang.Maybe;
import org.midheaven.lang.NotNullable;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;

/**
 * Represents an environment property name and type
 * The values are obtain using a {@link EnvironmentService}.
 * Is expected to use an enumerable class - not a Java enum - to group the existing properties in the application
 *
 * @param <T> the type of the property
 */
public @NotNullable interface EnvironmentProperty<T> {
    
    static EnvironmentProperty<Boolean> ofBoolean(String name) {
        return new TypedEnvironmentProperty<>(name, Boolean.class, text -> !text.equalsIgnoreCase("false") && !text.equals("0"));
    }
    
    static EnvironmentProperty<String> ofString(String name) {
        return new TypedEnvironmentProperty<>(name, String.class, Function.identity());
    }
    
    static EnvironmentProperty<Integer> ofInteger(String name) {
        return new TypedEnvironmentProperty<>(name, Integer.class, Integer::parseInt);
    }
    
    static EnvironmentProperty<LocalDate> ofDate(String name) {
        return new TypedEnvironmentProperty<>(name, LocalDate.class, LocalDate::parse);
    }
    
    static EnvironmentProperty<Duration> ofDuration(String name, TemporalUnit unit) {
        return new DurationEnvironmentProperty(name, unit);
    }
    
    String name();
    
    Class<T> type();
    
    Maybe<T> readFrom(String text);
}


record TypedEnvironmentProperty<T>(String name, Class<T> type, Function<String, T> parser) implements EnvironmentProperty<T> {
    
    @Override
    public Maybe<T> readFrom(String text) {
        return Maybe.of(parser.apply(text));
    }
}

record DurationEnvironmentProperty(String name, TemporalUnit unit) implements EnvironmentProperty<Duration> {
    
    @Override
    public Class<Duration> type() {
        return Duration.class;
    }
    
    @Override
    public Maybe<Duration> readFrom(String text) {
        return Maybe.of(Duration.of(Long.parseLong(text), unit));
    }
}