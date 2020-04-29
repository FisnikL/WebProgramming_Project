package com.finkicommunity.security;

public class JwtProperties {
    public static final String SECRET  = "finkicommunity2020";
    public static final int EXPIRATION_TIME = 20 * 60 * 10000; // 20 min
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
