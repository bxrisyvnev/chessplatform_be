package org.example.chessplatformbe.config.security;

public final class SecurityConstants {
    private SecurityConstants() {}

    public static final String USERS_ENDPOINT = "/users";
    public static final String AUTH_ENDPOINT = "/auth/**";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ARTICLE_ENDPOINT = "/articles/**";
    public static final String COMMENT_ENDPOINT = "/comments";
}