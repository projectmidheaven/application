package org.midheaven.application.security.permission;

import org.midheaven.collections.Enumerator;

final class EmptyPermissionSet implements PermissionSet {

    static final EmptyPermissionSet INSTANCE = new EmptyPermissionSet();

    @Override
    public Permission reduce() {
        return this;
    }

    @Override
    public Enumerator<org.midheaven.application.security.permission.Permission> enumerator() {
        return Enumerator.empty();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean implies(org.midheaven.application.security.permission.Permission other) {
        return other instanceof org.midheaven.application.security.permission.PermissionSet set && set.isEmpty();
    }

    @Override
    public org.midheaven.application.security.permission.PermissionSet union(org.midheaven.application.security.permission.PermissionSet other){
        return other;
    }

    @Override
    public org.midheaven.application.security.permission.PermissionSet intersection(org.midheaven.application.security.permission.PermissionSet other){
        return this;
    }
}
