package org.example.chessplatformbe.exceptions;

public class InvalidUserException extends Exception {

    public InvalidUserException(Integer id) {
        super(id + " is not a valid user");
    }

    public InvalidUserException(String username) {
        super(username + " is not a valid user");
    }
}
