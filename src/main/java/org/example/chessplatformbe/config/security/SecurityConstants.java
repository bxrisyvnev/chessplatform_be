package org.example.chessplatformbe.config.security;

public final class SecurityConstants {
    private SecurityConstants() {}

    public static final String REGISTER_ENDPOINT = "/api/register";
    public static final String LOGIN_ENDPOINT = "/api/login";
    public static final String LOGOUT_ENDPOINT = "/api/logout";
    public static final String USERS_ENDPOINT = "/api/users";
    public static final String AUTH_ENDPOINT = "/api/auth/**";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ARTICLE_ENDPOINT = "/api/articles/**";
    public static final String COMMENT_ENDPOINT = "/api/comments";
    public static final String GET_COMMENTS_ENDPOINT = "/api/comments/**";
    public static final String STREAM_ENDPOINT = "/api/streams";
    public static final String GET_STREAMS_ENDPOINT = "/api/streams/**";
    public static final String SPECTATE_ENDPOINT = "/api/spectate/**";
    public static final String NEWS_ENDPOINT = "/api/news";
    public static final String CHESS_ENDPOINT = "/api/chess";
    public static final String REPORT_ENDPOINT = "/api/reports";
    public static final String PROFILE_ENDPOINT = "/api/users/profile/**";
    public static final String WEBSOCKET_ENDPOINT = "/api/ws/**";
}
