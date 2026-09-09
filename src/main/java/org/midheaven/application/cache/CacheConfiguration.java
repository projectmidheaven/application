package org.midheaven.application.cache;

import org.midheaven.lang.Maybe;

import java.time.Duration;

public interface CacheConfiguration {
    
    static CacheConfigurationBuilder builder(){
        return new CacheConfigurationBuilder();
    }
    
    static CacheConfiguration withTtl(Duration duration){
        return new SimpleConfiguration(duration, null);
    }
    
    static CacheConfiguration withLimit(int count){
        return new SimpleConfiguration(null, count);
    }
    
    Maybe<Duration> ttl();
    Maybe<Integer> countLimit();
}
