package org.example.chessplatformbe.config.security.token;

public interface TokenDecoder {
    AccessToken decode(String tokenEncoded);
}
