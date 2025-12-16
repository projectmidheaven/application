package org.midheaven.application.context;

import java.util.function.Consumer;
import java.util.function.Function;

public interface CallService {

    <T> T call(Function<CallContext, T> action);
    void execute(Consumer<CallContext> action);
}
