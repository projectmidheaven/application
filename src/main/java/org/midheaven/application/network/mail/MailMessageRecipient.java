package org.midheaven.application.network.mail;


import org.midheaven.application.network.Email;

public record MailMessageRecipient(Email email, String name) {

}
