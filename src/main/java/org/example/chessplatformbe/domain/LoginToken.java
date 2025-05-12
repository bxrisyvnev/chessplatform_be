package org.example.chessplatformbe.domain;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginToken {
    private String accessToken;
}
