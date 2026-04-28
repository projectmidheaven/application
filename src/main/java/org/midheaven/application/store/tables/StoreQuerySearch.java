package org.midheaven.application.store.tables;

import java.util.function.Consumer;


public interface StoreQuerySearch {
    
    StoreQuerySearch match(Consumer<QueryWhere> matcher);
    
    StoreQuerySearch sorted(Consumer<QueryOrder> order);
    
    StoreQuery query();
}