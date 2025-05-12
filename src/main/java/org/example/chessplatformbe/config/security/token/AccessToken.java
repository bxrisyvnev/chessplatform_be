package org.example.chessplatformbe.config.security.token;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;


public class AccessToken {

    private String subject;
    private int userId;
    private final Set<String> roles;

    public AccessToken(String subject, int userId, Collection<String> roles) {
        if (subject == null || subject.isEmpty() || subject.isBlank()){
            throw new IllegalArgumentException("Token subject cannot be empty.");
        }
        this.subject = subject;
        this.userId = userId;
        this.roles = roles != null ? Set.copyOf(roles) : Collections.emptySet();
    }

    public String getSubject() {
        return subject;
    }

    public int getUserId() {
        return userId;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public boolean hasRole(String roleName) {
        return this.roles.contains(roleName);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessToken token = (AccessToken) o;
        return getSubject().equals(token.getSubject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubject());
    }
}
