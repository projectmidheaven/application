package org.midheaven.application.cache;

public interface CacheManager {
    
    CacheManager configure(CacheName name, CacheConfiguration configuration);
    
    Cache resolveCache(CacheName name);
}
