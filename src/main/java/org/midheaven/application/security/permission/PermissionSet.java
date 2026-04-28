package org.midheaven.application.security.permission;

import org.midheaven.collections.Enumerable;

public interface PermissionSet extends Permission, Enumerable<Permission> {
    
    static PermissionSet of(Permission permission){
        return new HashPermissionSet(permission);
    }
    
    static PermissionSet of(Permission a, Permission b , Permission ... others){
        return new HashPermissionSet(others).addPermission(a).addPermission(b);
    }
    
    static PermissionSet of(Iterable<Permission> permissions){
        if (!permissions.iterator().hasNext()){
            return empty();
        }
        return new HashPermissionSet(permissions);
    }

    static PermissionSet of(Enumerable<Permission> permissions){
        if (permissions.isEmpty()){
            return empty();
        }
        return new HashPermissionSet(permissions);
    }

    static PermissionSet empty(){
        return org.midheaven.application.security.permission.EmptyPermissionSet.INSTANCE;
    }
    
    boolean isEmpty();

    Permission reduce();

    default PermissionSet union(PermissionSet other){
        return PermissionsSupport.union(this, other);
    }

    default PermissionSet intersection(PermissionSet other){
        return PermissionsSupport.intersection(this, other);
    }
}
