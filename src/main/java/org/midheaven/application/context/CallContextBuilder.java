package org.midheaven.application.context;

import org.midheaven.application.security.PermitedSubject;
import org.midheaven.application.security.Subject;
import org.midheaven.application.security.SubjectIdentity;
import org.midheaven.application.security.permission.PermissionSet;
import org.midheaven.culture.Culture;

import java.time.Clock;
import java.time.LocalDateTime;

public final class CallContextBuilder {
    
    static final PermitedSubject SYSTEM = new PermitedSubject(
        new SubjectIdentity(){},
        PermissionSet.of(permission -> true)
    );
    
    private final Subject subject;
    private Culture culture;
    
    CallContextBuilder(Subject subject){
        this.subject = subject;
    }
    
    public CallContextBuilder withCulture(Culture culture){
        this.culture = culture;
        return this;
    }
    
    public CallContext now(Clock clock){
        return at(LocalDateTime.now(clock));
    }
    
    public CallContext at(LocalDateTime timestamp){
        return new CurrentCallContext(
            timestamp,
            culture == null ? Culture.parse("pt_BR") : culture,
            subject == null ? new PermitedSubject(new SubjectIdentity(){}, PermissionSet.empty()) : subject
        );
    }
 
}

record CurrentCallContext(LocalDateTime timestamp, Culture culture, Subject subject) implements CallContext {

}