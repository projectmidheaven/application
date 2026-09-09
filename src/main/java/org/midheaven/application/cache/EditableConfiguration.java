package org.midheaven.application.cache;

import org.midheaven.lang.Maybe;

import java.time.Duration;

record SimpleConfiguration( Duration aTtl, Integer aCountLimit) implements CacheConfiguration{
    
    
    @Override
    public Maybe<Duration> ttl() {
        return Maybe.of(aTtl);
    }
    
    @Override
    public Maybe<Integer> countLimit() {
        return Maybe.of(aCountLimit);
    }
    
}
