package org.example.chessplatformbe.exceptions;

public class InvalidArticleException extends Exception {
    public InvalidArticleException(String title) {
        super(title + " is not an existing article title");
    }
}
