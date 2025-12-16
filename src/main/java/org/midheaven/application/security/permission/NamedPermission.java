package org.midheaven.application.security.permission;


import java.util.Objects;

public final class NamedPermission implements Permission {

    public static NamedPermission name(String name){
        Objects.requireNonNull(name);
        return new NamedPermission(name);
    }

    private final String name;

    private NamedPermission(String name){
        this.name = name;
    }

    @Override
    public boolean implies(org.midheaven.application.security.permission.Permission other) {
        return PermissionsSupport.implies(this, other, Object::equals);
    }

    @Override
    public boolean equals(Object other){
        return other instanceof NamedPermission named
                && this.name.equals(named.name);
    }

    @Override
    public int hashCode(){
        return this.name.hashCode();
    }

    @Override
    public String toString(){
        return this.name;
    }
}
