package org.midheaven.application.network.mail;

import org.midheaven.application.network.Email;
import org.midheaven.collections.ResizableSequence;
import org.midheaven.collections.Sequence;
import org.midheaven.io.ByteContent;

public class MailMessage {
    
    private Email from;
    private ResizableSequence<MailMessageRecipient> to = Sequence.builder().resizable().empty();
    private ResizableSequence<MailMessageRecipient> cc = Sequence.builder().resizable().empty();
    private ResizableSequence<MailMessageRecipient> bcc = Sequence.builder().resizable().empty();
    private ResizableSequence<ByteContent> bodies = Sequence.builder().resizable().empty();
    private String subject;
    
    public Email getFrom() {
        return from;
    }
    
    public void setFrom(Email from) {
        this.from = from;
    }
    
    public Sequence<MailMessageRecipient> getToRecipients() {
        return to;
    }
    
    public void addRecipient(MailMessageRecipient recipient) {
        this.to.add(recipient);
    }
    
    public void addRecipients(Sequence<MailMessageRecipient> recipients) {
        this.to.addAll(recipients);
    }
    
    public Sequence<MailMessageRecipient> getCc() {
        return cc;
    }
    
    public void addCc(Sequence<MailMessageRecipient> cc) {
        this.cc.addAll(cc);
    }
    
    public Sequence<MailMessageRecipient> getBcc() {
        return bcc;
    }
    
    public void addBcc(Sequence<MailMessageRecipient> bcc) {
        this.bcc.addAll(bcc);
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public Sequence<ByteContent> getBodies() {
        return bodies;
    }
    
    public void addBody(ByteContent body) {
        this.bodies.add(body);
    }
}
