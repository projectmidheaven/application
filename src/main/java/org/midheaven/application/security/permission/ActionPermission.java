package org.midheaven.application.security.permission;

import org.midheaven.lang.Check;
import org.midheaven.lang.NotNullable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class ActionPermission implements Permission {
    
    public interface PermittableResourceAction {
        
        static PermittableResourceAction named(@NotNullable String name){
            return () -> name;
        }
        
        @NotNullable String name();
        
        default @NotNullable  PermittableResourceActionSet and(@NotNullable PermittableResourceAction other){
            Check.argumentIsNotNull(other, "other");
            return new SimplePermittableResourceActionSet(Set.of(other));
        }
    }
    
    public interface PermittableResourceActionSet extends Iterable<PermittableResourceAction> {
        
        static PermittableResourceActionSet of(PermittableResourceAction ... actions){
            return new SimplePermittableResourceActionSet(Set.of(actions));
        }
        
        PermittableResourceActionSet and(PermittableResourceAction other);
        PermittableResourceActionSet andAll(PermittableResourceActionSet otherSet);
        
        PermittableResourceActionSet exclude(PermittableResourceAction other);
        PermittableResourceActionSet excludeAll(PermittableResourceActionSet otherSet);
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

record SimplePermittableResourceActionSet(Set<ActionPermission.PermittableResourceAction> actions) implements ActionPermission.PermittableResourceActionSet {
    
    @Override
    public Iterator<ActionPermission.PermittableResourceAction> iterator() {
        return actions.iterator();
    }
    
    @Override
    public ActionPermission.PermittableResourceActionSet and(ActionPermission.PermittableResourceAction other) {
        if (this.actions.contains(other)) {
            return this;
        }
        var set = new HashSet<>(this.actions);
        set.add(other);
        return new SimplePermittableResourceActionSet(set);
    }
    
    @Override
    public ActionPermission.PermittableResourceActionSet andAll(ActionPermission.PermittableResourceActionSet otherSet) {
        var set = new HashSet<>(this.actions);
        for (var item : otherSet){
            set.add(item);
        }
        return new SimplePermittableResourceActionSet(set);
    }
    
    @Override
    public ActionPermission.PermittableResourceActionSet exclude(ActionPermission.PermittableResourceAction other) {
        if (!this.actions.contains(other)) {
            return this;
        }
        var set = new HashSet<>(this.actions);
        set.remove(other);
        return new SimplePermittableResourceActionSet(set);
    }
    
    @Override
    public ActionPermission.PermittableResourceActionSet excludeAll(ActionPermission.PermittableResourceActionSet otherSet) {
        var set = new HashSet<>(this.actions);
        for (var item : otherSet){
            set.remove(item);
        }
        return new SimplePermittableResourceActionSet(set);
    }
}
