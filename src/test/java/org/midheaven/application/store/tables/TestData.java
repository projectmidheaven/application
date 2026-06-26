package org.midheaven.application.store.tables;

import org.midheaven.application.network.Email;

interface TestData {
    
    Long getId();
    void setId(Long id);
    
    String getName();
    void setName(String name);
    
    Email getEmail();
    void setEmail(Email name);
    

}
