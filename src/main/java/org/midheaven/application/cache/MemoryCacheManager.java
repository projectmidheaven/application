package org.midheaven.application.cache;

import org.midheaven.lang.Maybe;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public final class MemoryCacheManager extends  AbstractCacheManager {
    
    private final Clock clock;
    
    public MemoryCacheManager(){
        this(Clock.systemUTC());
    }
    
    public MemoryCacheManager(Clock clock){
        this.clock = clock;
    }
    
    @Override
    protected Cache resolveCache(CacheName name, CacheConfiguration configuration) {
        return new MemoryCache(configuration, clock);
    }
    
}

class MemoryCache implements Cache {
    
    private final CacheConfiguration configuration;
    private final ConcurrentHashMap<String, CachedValue> cacheValues = new ConcurrentHashMap<>();
    private final Clock clock;
    
    MemoryCache(CacheConfiguration configuration, Clock clock){
        this.configuration = configuration;
        this.clock = clock;
    }
    
    @Override
    public <T> void store(String key, T value) {
        if (configuration.countLimit().isAbsent() || cacheValues.size() < configuration.countLimit().get()){
            cacheValues.put(key, new CachedValue(value, clock.instant()));
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> Maybe<T> retrieve(String key) {
        
        var cachedValue = cacheValues.get(key);
        if ( cachedValue  == null){
            return Maybe.none();
        } else if (isExpired(cachedValue)){
            cacheValues.remove(key);
            return Maybe.none();
        }
        
        return Maybe.of(cachedValue).map(it -> (T)it.value());
    }
    
    private boolean isExpired(CachedValue cachedValue){
        if (configuration.ttl().isAbsent()){
            return false;
        }
        return Duration.between(cachedValue.cachedAt(), clock.instant()).compareTo(configuration.ttl().get()) >= 0;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> Maybe<T> computeIfAbsent(String key, Function<String, T> resolver) {
        var cached = this.<T>retrieve(key);
        
        if(cached.isAbsent()){
            return Maybe.of(cacheValues.computeIfAbsent(key, k -> new CachedValue(resolver.apply(k),clock.instant())))
                       .map(it -> (T)it.value());
        } else {
            return cached;
        }
        
    }
    
    @Override
    public void remove(String key) {
        cacheValues.remove(key);
    }
}

record CachedValue(Object value, Instant cachedAt) { }