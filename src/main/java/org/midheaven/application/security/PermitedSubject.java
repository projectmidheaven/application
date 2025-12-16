package org.midheaven.application.security;

public final class PermitedSubject extends Subject {

    public static PermitedSubject authenticated(SubjectIdentity identity){
        return new PermitedSubject(identity, org.midheaven.application.security.permission.PermissionSet.empty());
    }

    public static PermitedSubject authorized(SubjectIdentity identity, org.midheaven.application.security.permission.PermissionSet permissions){
        return new PermitedSubject(identity, permissions);
    }

    private final org.midheaven.application.security.permission.PermissionSet permissions;
    private final SubjectIdentity identity;

    public PermitedSubject(SubjectIdentity identity, org.midheaven.application.security.permission.PermissionSet permissions){
        this.identity = identity;
        this.permissions = permissions;
    }

    @Override
    public SubjectIdentity identity() {
        return identity;
    }
    
    @Override
    public boolean hasPermission(org.midheaven.application.security.permission.Permission permission) {
        return this.permissions.implies(permission);
    }
}
