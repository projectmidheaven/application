package org.midheaven.application.security.permission;

import org.midheaven.lang.Check;
import org.midheaven.lang.NotNullable;

public final class ActionPermission implements Permission {
    
    public interface PermittableResourceAction {
        String name();
    }
    
    public static @NotNullable ActionPermission over(@NotNullable String protectedResource,@NotNullable PermittableResourceAction action){
        Check.argumentIsNotNull(protectedResource, "protectedResource");
        Check.argumentIsNotNull(action, "action");
        return new ActionPermission(protectedResource, action.name().toLowerCase());
    }
    
    final String protectedResource;
    final String actionName;
    
    private ActionPermission(String protectedResource, String actionName) {
        this.protectedResource = protectedResource;
        this.actionName = actionName;
    }
    
    public String protectedResource(){
        return protectedResource;
    }
    
    public String actionName(){
        return actionName;
    }
    
    @Override
    public boolean implies(Permission other) {
        return PermissionsSupport.implies(this, other, Object::equals);
    }
    
    @Override
    public boolean equals(Object other){
        return other instanceof ActionPermission actionPermission
                   && this.actionName.equals(actionPermission.actionName)
                   && this.protectedResource.equals(actionPermission.protectedResource);
    }
    
    @Override
    public int hashCode(){
        return this.actionName.hashCode();
    }
    
    @Override
    public String toString(){
        return this.protectedResource + "." + this.actionName;
    }
}
