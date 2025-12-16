package org.midheaven.application.security;

public sealed class Credential permits PasswordCredential, UserkeyCredential, UsernameCredential {
}

