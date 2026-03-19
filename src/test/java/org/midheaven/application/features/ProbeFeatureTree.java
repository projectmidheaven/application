package org.midheaven.application.features;

public class ProbeFeatureTree extends FeatureTree {
    
    public ProbeFeatureTree(){
        addRoot(Feature.named("user")
            .add(Feature.named("session")
                 .add(Feature.named("login"))
                 .add(Feature.named("logout"))
            )
            .add(Feature.named("account")
                 .add(Feature.named("create"))
                 .add(Feature.named("delete"))
                 .add(Feature.named("revoke"))
            )
        )
       ;
       addRoot(Feature.named("customer")
           .add(Feature.named("account")
                .add(Feature.named("create"))
                .add(Feature.named("delete"))
                .add(Feature.named("revoke"))
           )
       )
       ;
       
    }

    
}
