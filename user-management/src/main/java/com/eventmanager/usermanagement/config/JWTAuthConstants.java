package com.eventmanager.usermanagement.config;

public class JWTAuthConstants
{
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String SECRET = "wpd1ugn3raw8eg";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}
