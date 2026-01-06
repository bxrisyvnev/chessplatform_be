package org.example.chessplatformbe.config.security;

public final class SecurityConstants {
    private SecurityConstants() {}

    public static final String REGISTER_ENDPOINT = "/register";
    public static final String LOGIN_ENDPOINT = "/login";
    public static final String LOGOUT_ENDPOINT = "/logout";
    public static final String USERS_ENDPOINT = "/users";
    public static final String AUTH_ENDPOINT = "/auth/**";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ARTICLE_ENDPOINT = "/articles/**";
    public static final String COMMENT_ENDPOINT = "/comments";
    public static final String GET_COMMENTS_ENDPOINT = "/comments/**";
    public static final String STREAM_ENDPOINT = "/streams";
    public static final String GET_STREAMS_ENDPOINT = "/streams/**";
    public static final String SPECTATE_ENDPOINT = "/spectate/**";
    public static final String NEWS_ENDPOINT = "/news";
    public static final String CHESS_ENDPOINT = "/chess";
    public static final String REPORT_ENDPOINT = "/reports";
    public static final String PROFILE_ENDPOINT = "/users/profile/**";
    public static final String WEBSOCKET_ENDPOINT = "/ws/**";
}
