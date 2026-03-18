package org.midheaven.application.security.permision;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.midheaven.application.security.permission.NamedPermission;
import org.midheaven.application.security.permission.PermissionSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PermissionTestCases {
    
    @Test
    public void singleImplications(){
        var a = NamedPermission.name("A");
        var b = NamedPermission.name("B");
        
        assertTrue(a.implies(a), "Permission does not imply it self");
        assertFalse(b.implies(a), "Permission implies unrelated permission");
        assertFalse(a.implies(b), "Permission implies unrelated permission");
        assertTrue(a.implies(NamedPermission.name("A")), "Permission does not imply equal permission");
      
    }
    
    @Test
    public void setImplications(){
        var a = NamedPermission.name("A");
        var b = NamedPermission.name("B");
        var c = NamedPermission.name("C");
        
        var empty = PermissionSet.empty();
        var setA = PermissionSet.of(a);
        var setB = PermissionSet.of(b);
        var setC = PermissionSet.of(c);
        var setAB = PermissionSet.of(a, b);
        
        assertTrue(setA.implies(a));
        assertTrue(setA.implies(setA));
        assertTrue(setAB.implies(setA));
        assertTrue(setAB.implies(setB));
        assertFalse(setAB.implies(setC));
        assertFalse(setA.implies(setAB));
        
        assertFalse(setC.implies(a));
        assertFalse(setC.implies(b));
        assertTrue(setC.implies(c));
        
        assertTrue(setA.implies(empty));
        assertTrue(a.implies(empty));
        assertTrue(setAB.implies(empty));
        assertFalse(empty.implies(setA));
        assertFalse(empty.implies(a));
        assertFalse(empty.implies(setAB));
    }
}
