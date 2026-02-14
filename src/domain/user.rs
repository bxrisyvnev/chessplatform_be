/*
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
*/

#[derive(Debug, Clone)]
pub struct User {
    pub id: i32,
    pub username: &str,
    pub password: &str,
    pub age: i8,
    pub display_name: &str,
    pub natioality: &str,
}
