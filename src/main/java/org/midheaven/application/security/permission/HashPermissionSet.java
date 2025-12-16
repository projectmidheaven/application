package org.midheaven.application.security.permission;


import org.midheaven.collections.DistinctAssortment;
import org.midheaven.collections.Enumerator;
import org.midheaven.collections.ResizableDistinctAssortment;

final class HashPermissionSet implements PermissionSet {

    private final ResizableDistinctAssortment<Permission> permissions = DistinctAssortment.builder().resizable().empty();

    HashPermissionSet(){}

    HashPermissionSet(Iterable<org.midheaven.application.security.permission.Permission> other){
        for ( var p : other){
            this.addPermission(p);
        }
    }

    HashPermissionSet(org.midheaven.application.security.permission.Permission... permissions){
        for ( var p : permissions){
            this.addPermission(p);
        }
    }
    @Override
    public org.midheaven.application.security.permission.Permission reduce() {
        if (permissions.count().isOne()){
            return permissions.first().orElseThrow();
        }
        return this;
    }

    @Override
    public boolean isEmpty() {
        return permissions.isEmpty();
    }

    @Override
    public boolean implies(org.midheaven.application.security.permission.Permission other) {
        if (other instanceof org.midheaven.application.security.permission.PermissionSet set){
            // a set implies another set if all other permissions in the set are implied by permissions in this set
            for (var permission : set){
                if (!permissions.anyMatch(it -> it.implies(permission))){
                    return false;
                }
            }
            return true;
        }
        // a set implies a permission if one of its permissions implies that other permission
        return permissions.anyMatch(it -> it.implies(other));
    }

    @Override
    public Enumerator<org.midheaven.application.security.permission.Permission> enumerator() {
        return permissions.enumerator();
    }

    public HashPermissionSet addPermission(org.midheaven.application.security.permission.Permission permission){
        if (permission instanceof org.midheaven.application.security.permission.PermissionSet set){
            permissions.addAll(set);
        } else {
            permissions.add(permission);
        }
        return this;
    }

    public HashPermissionSet removePermission(org.midheaven.application.security.permission.Permission permission){
        if (permission instanceof org.midheaven.application.security.permission.PermissionSet set){
            permissions.removeAll(set);
        } else {
            permissions.remove(permission);
        }
        return this;
    }

    public HashPermissionSet retainAll(org.midheaven.application.security.permission.PermissionSet other) {
        this.permissions.retainAll(other);
        return this;
    }
}
