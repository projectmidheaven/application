package org.midheaven.application.security.permission;


import org.midheaven.collections.DistinctAssortment;
import org.midheaven.collections.Enumerator;
import org.midheaven.collections.ResizableDistinctAssortment;

final class HashPermissionSet implements PermissionSet {

    private final ResizableDistinctAssortment<Permission> permissions = DistinctAssortment.builder().resizable().empty();

    HashPermissionSet(){}

    HashPermissionSet(Iterable<Permission> other){
        for ( var p : other){
            this.addPermission(p);
        }
    }

    HashPermissionSet(Permission... permissions){
        for ( var p : permissions){
            this.addPermission(p);
        }
    }
    @Override
    public Permission reduce() {
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
    public boolean implies(Permission other) {
        if (other instanceof PermissionSet set){
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
    public Enumerator<Permission> enumerator() {
        return permissions.enumerator();
    }

    public HashPermissionSet addPermission(Permission permission){
        if (permission instanceof PermissionSet set){
            permissions.addAll(set);
        } else {
            permissions.add(permission);
        }
        return this;
    }

    public HashPermissionSet removePermission(Permission permission){
        if (permission instanceof PermissionSet set){
            permissions.removeAll(set);
        } else {
            permissions.remove(permission);
        }
        return this;
    }

    public HashPermissionSet retainAll(PermissionSet other) {
        this.permissions.retainAll(other);
        return this;
    }
}
