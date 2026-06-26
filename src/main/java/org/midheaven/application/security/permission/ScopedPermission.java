package org.midheaven.application.security.permission;

import org.midheaven.lang.Check;
import org.midheaven.lang.NotNullable;

public final class ScopedPermission implements Permission{
    
    public static ScopedPermission scope(@NotNullable String scope, @NotNullable Permission permission) {
        return new ScopedPermission(
            Check.argumentIsNotNull(scope),
            Check.argumentIsNotNull(permission)
        );
    }
    
    private final Permission permission;
    private final String scope;
    
    private ScopedPermission(String scope, Permission permission){
        this.scope = scope;
        this.permission = permission;
    }
    
    public Permission permission() {
        return permission;
    }
    
    public String scope() {
        return scope;
    }
    
    @Override
    public boolean implies(Permission other) {
        if (other instanceof PermissionSet emptySet && emptySet.isEmpty()){
            return true;
        }
        return other instanceof ScopedPermission scopedPermission
            && this.scope.equals(scopedPermission.scope)
            && this.permission.implies(scopedPermission.permission);
    }
    
    @Override
    public boolean equals(Object other){
        return other instanceof ScopedPermission scopedPermission
                   && this.scope.equals(scopedPermission.scope)
                   && this.permission.equals(scopedPermission.permission);
    }
    
    @Override
    public int hashCode(){
        return this.permission.hashCode();
    }
    
    @Override
    public String toString(){
        return this.permission.toString() + "(" + this.scope + ")";
    }
}
