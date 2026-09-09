package org.midheaven.application.cache;

import org.midheaven.lang.Maybe;

import java.util.function.Function;

public interface Cache {
    
    <T> void store(String key, T value);
    <T> Maybe<T> retrieve(String key);
    
    <T> Maybe<T> computeIfAbsent(String key, Function<String, T> resolver);
    
   void remove(String key);
}
