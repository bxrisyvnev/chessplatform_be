package org.example.chessplatformbe.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {

    private Integer id;

    private String username;

    private String password;

    private Integer age;

    private String displayName;

    private String nationality;
}
