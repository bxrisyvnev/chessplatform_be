package org.example.chessplatformbe.config.security.token;

public interface TokenEncoder{
    String encode(AccessToken token);
}
