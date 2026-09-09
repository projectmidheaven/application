package org.midheaven.application.cache;

import java.time.Duration;

public final class CacheConfigurationBuilder {
    
    Duration duration;
    Integer count;
    
    CacheConfigurationBuilder(){}
    
    public CacheConfigurationBuilder withTtl(Duration duration){
        this.duration = duration;
        return this;
    }
    
    public CacheConfigurationBuilder withLimit(int count){
        if (count < 0){
            count = 0;
        }
        this.count = count;
        return this;
    }
    
    public CacheConfiguration build(){
        return new SimpleConfiguration(duration, count);
    }
    
}
