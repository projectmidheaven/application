package org.midheaven.application.context;


import org.midheaven.application.security.Subject;
import org.midheaven.culture.Culture;

import java.time.LocalDateTime;

public interface CallContext {

    static CallContextBuilder anonymous() {
        return new CallContextBuilder(null);
    }
    static CallContextBuilder system() {
        return new CallContextBuilder(CallContextBuilder.SYSTEM);
    }
    static CallContextBuilder identified(Subject subject) {
        return new CallContextBuilder(subject);
    }
    
    
    Culture culture();
    LocalDateTime timestamp();
    Subject subject();
}
