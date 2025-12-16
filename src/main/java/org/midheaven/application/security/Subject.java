package org.midheaven.application.security;

import org.midheaven.application.security.permission.Permission;
import org.midheaven.lang.Check;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.NotNullable;

public sealed abstract class Subject permits PermitedSubject {
    
    public abstract @NotNullable SubjectIdentity identity();
    public abstract boolean hasPermission(@NotNullable Permission permission);
    
    public <I extends SubjectIdentity> Maybe<I> identityAs(@NotNullable Class<I> subjectIdentityType){
        Check.argumentIsNotNull("subjectIdentityType", subjectIdentityType);
        return Maybe.of(identity())
                   .filter(subjectIdentityType::isInstance)
                   .map(subjectIdentityType::cast);
    }
    
    public boolean hasAnyPermission(@NotNullable Permission a, @NotNullable Permission b, @NotNullable Permission ... others){
        var hasPermission = hasPermission(a) || hasPermission(b);
        if (hasPermission){
            return true;
        }
        
        for (var p : others){
            if (hasPermission(p)){
                return true;
            }
        }
        return false;
    }
    
    public boolean hasAllPermissions(Permission a, Permission b, Permission ... others){
        var hasPermission = hasPermission(a) && hasPermission(b);
        if (!hasPermission){
            return false;
        }
        
        for (var p : others){
            if (!hasPermission(p)){
                return false;
            }
        }
        return true;
    }
}
