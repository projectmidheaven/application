package org.midheaven.application.security.permission;

import java.util.function.BiPredicate;

public class PermissionsSupport {

    public static boolean implies(Permission a , Permission b, BiPredicate<Permission, Permission> singleImplies){
        if ( a == null || b == null){
            return false;
        } else if ( a == b){
            return true;
        }

        a = a instanceof PermissionSet setA ? setA.reduce() : a;
        b = b instanceof PermissionSet setB ? setB.reduce() : b;

        if (a instanceof PermissionSet setA){
            if (b instanceof PermissionSet setB){
                return implies(setA, setB, singleImplies);
            }
            return implies(setA, b, singleImplies);
        } else {
            if (b instanceof PermissionSet setB){
                return implies(a, setB, singleImplies);
            }
            return singleImplies.test(a, b);
        }
    }

    static private boolean implies(Permission a , PermissionSet b, BiPredicate<Permission, Permission> singleImplies){
        return b.isEmpty();
    }

    static private boolean implies(PermissionSet a , Permission b, BiPredicate<Permission, Permission> singleImplies){
        for (var p : a){
            if (p.implies(b)){
                return true;
            }
        }
        return false;
    }

    static private boolean implies(PermissionSet a , PermissionSet b, BiPredicate<Permission, Permission> singleImplies){
        // every in b is implied
        for (var pb : b){
            if (!a.anyMatch(it -> it.implies(b))){
                return false;
            }
        }
        return true;
    }

    static PermissionSet union(PermissionSet a, PermissionSet b) {
        if (a.isEmpty()){
            return b.isEmpty() ? PermissionSet.empty() : b;
        } else if (b.isEmpty()){
            return a;
        }
        return new org.midheaven.application.security.permission.HashPermissionSet(a).addPermission(b);
    }

    static PermissionSet intersection(PermissionSet a, PermissionSet b) {
        if (a.isEmpty() || b.isEmpty()){
            return PermissionSet.empty();
        }
        return new org.midheaven.application.security.permission.HashPermissionSet(a).retainAll(b);
    }
}
