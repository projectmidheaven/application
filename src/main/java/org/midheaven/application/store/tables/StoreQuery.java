package org.midheaven.application.store.tables;

import org.midheaven.collections.Page;
import org.midheaven.collections.Sequence;
import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

public interface StoreQuery {
    
    Maybe<TableRow> find();
    Sequence<TableRow> all();
    boolean any();
    Int count();
    
    Page<TableRow> paginate(int pageOrdinal, int maxItemsPerPageCount);
}
