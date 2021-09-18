package com.eventmanager.usermanagement.domain.exceptions;

import org.springframework.security.core.AuthenticationException;

public class PasswordsDoNotMatchException extends AuthenticationException
{
    public PasswordsDoNotMatchException(String msg)
    {
        super(msg);
    }
}
