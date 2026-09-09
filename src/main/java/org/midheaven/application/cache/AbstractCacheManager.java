package org.midheaven.application.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractCacheManager implements CacheManager{
    
    private final Map<String, Cache> caches = new ConcurrentHashMap<>();
    private final Map<String, CacheConfiguration> cacheConfigurations = new ConcurrentHashMap<>();
    
    @Override
    public final CacheManager configure(CacheName name, CacheConfiguration configuration) {
        cacheConfigurations.put(name.name(), configuration);
        return this;
    }
    
    @Override
    public final Cache resolveCache(CacheName name) {
        var config = cacheConfigurations.get(name.name());
        if (config == null) {
            return caches.computeIfAbsent(name.name(), n -> resolveCache(name,new SimpleConfiguration(null,null)));
        }
        return caches.computeIfAbsent(name.name(), n -> resolveCache(name,config ))
        
  ;
    }
    
    protected abstract Cache resolveCache(CacheName name, CacheConfiguration configuration);
}
