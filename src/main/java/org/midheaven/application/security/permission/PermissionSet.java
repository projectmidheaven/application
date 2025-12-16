package org.midheaven.application.security.permission;

import org.midheaven.collections.Enumerable;

public interface PermissionSet extends org.midheaven.application.security.permission.Permission, Enumerable<org.midheaven.application.security.permission.Permission> {

    static PermissionSet of(Iterable<org.midheaven.application.security.permission.Permission> permissions){
        if (!permissions.iterator().hasNext()){
            return empty();
        }
        return new HashPermissionSet(permissions);
    }

    static PermissionSet of(Enumerable<org.midheaven.application.security.permission.Permission> permissions){
        if (permissions.isEmpty()){
            return empty();
        }
        return new HashPermissionSet(permissions);
    }

    static PermissionSet empty(){
        return org.midheaven.application.security.permission.EmptyPermissionSet.INSTANCE;
    }

    static PermissionSet of(org.midheaven.application.security.permission.Permission first, org.midheaven.application.security.permission.Permission... permissions){
        return new HashPermissionSet(permissions).addPermission(first);
    }

    boolean isEmpty();

    org.midheaven.application.security.permission.Permission reduce();

    default PermissionSet union(PermissionSet other){
        return PermissionsSupport.union(this, other);
    }

    default PermissionSet intersection(PermissionSet other){
        return org.midheaven.application.security.permission.PermissionsSupport.intersection(this, other);
    }
}
