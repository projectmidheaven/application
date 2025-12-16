package org.midheaven.application.context;


import org.midheaven.application.security.Subject;
import org.midheaven.culture.Culture;

import java.time.LocalDateTime;

public interface CallContext {

    Culture culture();
    LocalDateTime timestamp();
    Subject subject();
}
