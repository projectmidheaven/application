package org.midheaven.application.security.permission;

import java.util.function.BiPredicate;

class PermissionsSupport {

    static boolean implies(org.midheaven.application.security.permission.Permission a , org.midheaven.application.security.permission.Permission b, BiPredicate<org.midheaven.application.security.permission.Permission, org.midheaven.application.security.permission.Permission> singleImplies){
        if ( a == null || b == null){
            return false;
        } else if ( a == b){
            return true;
        }

        a = a instanceof org.midheaven.application.security.permission.PermissionSet setA ? setA.reduce() : a;
        b = b instanceof org.midheaven.application.security.permission.PermissionSet setB ? setB.reduce() : b;

        if (a instanceof org.midheaven.application.security.permission.PermissionSet setA){
            if (b instanceof org.midheaven.application.security.permission.PermissionSet setB){
                return implies(setA, setB, singleImplies);
            }
            return implies(setA, b, singleImplies);
        } else {
            if (b instanceof org.midheaven.application.security.permission.PermissionSet setB){
                return implies(a, setB, singleImplies);
            }
            return singleImplies.test(a, b);
        }
    }

    static private boolean implies(org.midheaven.application.security.permission.Permission a , org.midheaven.application.security.permission.PermissionSet b, BiPredicate<org.midheaven.application.security.permission.Permission, org.midheaven.application.security.permission.Permission> singleImplies){
        return b.isEmpty();
    }

    static private boolean implies(org.midheaven.application.security.permission.PermissionSet a , org.midheaven.application.security.permission.Permission b, BiPredicate<org.midheaven.application.security.permission.Permission, org.midheaven.application.security.permission.Permission> singleImplies){
        for (var p : a){
            if (p.implies(b)){
                return true;
            }
        }
        return false;
    }

    static private boolean implies(org.midheaven.application.security.permission.PermissionSet a , org.midheaven.application.security.permission.PermissionSet b, BiPredicate<org.midheaven.application.security.permission.Permission, org.midheaven.application.security.permission.Permission> singleImplies){
        // every in b is implied
        for (var pb : b){
            if (!a.anyMatch(it -> it.implies(b))){
                return false;
            }
        }
        return true;
    }

    static org.midheaven.application.security.permission.PermissionSet union(org.midheaven.application.security.permission.PermissionSet a, org.midheaven.application.security.permission.PermissionSet b) {
        if (a.isEmpty()){
            return b.isEmpty() ? org.midheaven.application.security.permission.PermissionSet.empty() : b;
        } else if (b.isEmpty()){
            return a;
        }
        return new org.midheaven.application.security.permission.HashPermissionSet(a).addPermission(b);
    }

    static org.midheaven.application.security.permission.PermissionSet intersection(org.midheaven.application.security.permission.PermissionSet a, org.midheaven.application.security.permission.PermissionSet b) {
        if (a.isEmpty() || b.isEmpty()){
            return org.midheaven.application.security.permission.PermissionSet.empty();
        }
        return new org.midheaven.application.security.permission.HashPermissionSet(a).retainAll(b);
    }
}
