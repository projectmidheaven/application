package org.midheaven.application.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.midheaven.time.EditableClock;
import org.midheaven.time.EditableClockProvider;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CacheTestCases {
    
    static final CacheName cacheName = () -> "test";
    static final LocalDateTime referenceTime = LocalDateTime.of(2021, 1, 1, 10, 0, 0);
    static final EditableClock clock = EditableClockProvider.newInstanceAtUtc(referenceTime).clock();
    static CacheManager cacheManager = new MemoryCacheManager(clock)
        .configure(cacheName, CacheConfiguration.builder().withTtl(Duration.ofMillis(20)).withLimit(1).build());
    ;

    @BeforeEach
    public void reset(){
        clock.set(referenceTime);
    }
    
    @Test
    public void computeIfAbsentGeneratesValue(){
        var cache = cacheManager.resolveCache(cacheName);
        
        var value = cache.computeIfAbsent("A", a -> "1");
        
        assertEquals("1", value.orNull(), "Value should be computed");
        
        assertEquals("1", cache.retrieve("A").orNull(), "Value should be present after storing");
        
    }
    
    @Test
    public void cacheRetrieveRespectsTtl(){
        var cache = cacheManager.resolveCache(cacheName);
        cache.store("A", "1");
        
        assertEquals("1", cache.retrieve("A").orNull(), "Value should be present after storing");
        
        clock.advanceBy(Duration.ofMillis(3));
        
        assertEquals("1", cache.retrieve("A").orNull(), "Value should be present after passing less than TTL time");
        
        clock.advanceBy(Duration.ofMillis(17));
        
        assertTrue(cache.retrieve("A").isAbsent(), "Value should be absent after the TTL time");
    }
    
    @Test
    public void cacheStoreRespectsLimit(){
  
        var cache = cacheManager.resolveCache(cacheName);
        cache.store("A", "1");
        
        assertEquals("1", cache.retrieve("A").orNull(), "Value should be present after storing");
        
        cache.store("B", "2");
        
        assertTrue(cache.retrieve("B").isAbsent(), "Second value should not be stored");
       
    }
}
