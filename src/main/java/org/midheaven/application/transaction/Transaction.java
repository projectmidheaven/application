package org.midheaven.application.transaction;

import java.util.function.Supplier;

public interface Transaction extends AutoCloseable{
    
    /**
     * Close transaction
     *
     * {@inheritDoc}
     *
     */
    void close(); // declared again to remove throws clause
    
    /**
     * Call to indicate the changes must be persisted. Otherwise, all changes will be rolled back.
     */
    void commit();
    
    /**
     * Executes the given {@link Supplier} inside the transaction and commits the results
     * @param supplier the action to run
     * @return the result of the action
     * @param <T> the type of the result
     */
    default <T> T commit(Supplier<T> supplier){
        try (this) {
            var result = supplier.get();
            this.commit();
            return result;
        }
    }
    
    /**
     * Executes the given {@link Runnable} inside the transaction and commits the results
     * @param runnable the action to run
     */
    default void commit(Runnable runnable){
        try (this) {
            runnable.run();
            this.commit();
        }
    }
}
