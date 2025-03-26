package org.example.chessplatformbe.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private Long id;

    private String username;

    private String hash;

    private String password;

    private int age;
    private String displayName;
    private String nationality;

    public User(String username, int age, String displayName, String nationality) {
        this.username = username;
        this.age = age;
        this.displayName = displayName;
        this.nationality = nationality;
    }
}
