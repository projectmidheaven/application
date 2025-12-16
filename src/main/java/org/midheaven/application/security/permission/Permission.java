package org.midheaven.application.security.permission;

public interface Permission {

    boolean implies(Permission other);
}
