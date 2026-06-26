package org.midheaven.application.network.mail;

public interface MailDeliveryService {
    
    void deliver(MailMessage mailMessage);
}
