package org.midheaven.application.features;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FeatureTestCases {
    
    FeatureTree tree = new ProbeFeatureTree();
    
    @Test
    public void addTwice(){
        var a = Feature.named("A");
        var b = Feature.named("B");
        var c = Feature.named("C");
        
        a.add(b);
        
        assertThrows(IllegalStateException.class, () -> c.add(b));
    }
    
    @Test
    public void resolution(){
        
        var path = FeaturePath.from("user-session-login");
        var login = tree.resolve(path);
        
        assertTrue(login.isPresent());
        login.ifPresent(it -> {
            assertEquals(path, it.path());
        });
        
        // search again
        login = tree.resolve(path);
        
        assertTrue(login.isPresent());
    }
}
