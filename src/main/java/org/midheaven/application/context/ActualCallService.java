package org.midheaven.application.context;

import org.midheaven.application.security.Subject;
import org.midheaven.application.security.SubjectResolver;
import org.midheaven.culture.Culture;
import org.midheaven.culture.CultureResolver;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ActualCallService implements CallService {

    private final Clock clock;
    private final SubjectResolver subjectResolver;
    private final CultureResolver cultureResolver;

    public ActualCallService(
        Clock clock,
        SubjectResolver subjectResolver,
        CultureResolver cultureResolver
    ){
        this.clock = clock;
        this.subjectResolver = subjectResolver;
        this.cultureResolver = cultureResolver;
    }

    private CallContext currentContext(){
        return new CurrentCallContext(
                LocalDateTime.now(clock),
                cultureResolver.resolveCulture(),
                subjectResolver.resolveSubject()
        );
    }

    @Override
    public <T> T call(Function<CallContext, T> action) {
        return action.apply(this.currentContext());
    }

    @Override
    public void execute(Consumer<CallContext> action) {
        action.accept(this.currentContext());
    }
}

record CurrentCallContext(LocalDateTime timestamp, Culture culture, Subject subject) implements CallContext {

}