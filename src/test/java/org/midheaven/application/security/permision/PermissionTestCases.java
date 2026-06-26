package org.midheaven.application.security.permision;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.midheaven.application.security.permission.ActionPermission;
import org.midheaven.application.security.permission.NamedPermission;
import org.midheaven.application.security.permission.PermissionSet;
import org.midheaven.application.security.permission.ScopedPermission;

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
        
        var emptySet = PermissionSet.empty();
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
        
        assertTrue(setA.implies(emptySet));
        assertTrue(a.implies(emptySet));
        assertTrue(setAB.implies(emptySet));
        assertFalse(emptySet.implies(setA));
        assertFalse(emptySet.implies(a));
        assertFalse(emptySet.implies(setAB));
    }
    
    @Test
    public void scopeImplicationCases(){
        var x = NamedPermission.name("X");
        var a = ScopedPermission.scope("A", x);
        var aa = ScopedPermission.scope("A", x);
        var aSet = ScopedPermission.scope("A", PermissionSet.of(x));
        var b = ScopedPermission.scope("B", x);
        var c = ScopedPermission.scope("A", NamedPermission.name("Y"));
        
        var emptySet = PermissionSet.empty();
        
        assertTrue(a.implies(emptySet));
        assertFalse(emptySet.implies(a));
        assertTrue(b.implies(emptySet));
        assertFalse(emptySet.implies(b));
     
        assertTrue(c.implies(emptySet));
        assertFalse(emptySet.implies(c));
        
        assertFalse(a.implies(b));
        assertFalse(a.implies(c));
        assertFalse(b.implies(a));
        assertFalse(b.implies(c));
        assertFalse(c.implies(a));
        assertFalse(c.implies(b));
        
        assertTrue(a.implies(aa));
        assertTrue(aa.implies(a));
        assertTrue(a.implies(aSet));
        assertTrue(aSet.implies(a));
    }
    
    @Test
    public void actionImplicationCases(){
        var a = ActionPermission.over("R1", () -> "op1");
        var aa = ActionPermission.over("R1", () -> "op1");
        var b = ActionPermission.over("R2", () -> "op1");
        var c = ActionPermission.over("R1", () -> "op2");
        
        var emptySet = PermissionSet.empty();
        
        assertTrue(a.implies(emptySet));
        assertFalse(emptySet.implies(a));
        assertTrue(b.implies(emptySet));
        assertFalse(emptySet.implies(b));
        
        assertTrue(c.implies(emptySet));
        assertFalse(emptySet.implies(c));
        
        assertFalse(a.implies(b));
        assertFalse(a.implies(c));
        assertFalse(b.implies(a));
        assertFalse(b.implies(c));
        assertFalse(c.implies(a));
        assertFalse(c.implies(b));
        
        assertTrue(a.implies(aa));
        assertTrue(aa.implies(a));
    }
}
