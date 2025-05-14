package org.example.chessplatformbe.config.security.token;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;


@Getter
public class AccessToken {

    private final String subject;
    private final int userId;
    private final Set<String> roles;

    public AccessToken(String subject, int userId, Collection<String> roles) {
        if (subject == null || subject.isEmpty() || subject.isBlank()){
            throw new IllegalArgumentException("Token subject cannot be empty.");
        }
        this.subject = subject;
        this.userId = userId;
        this.roles = roles != null ? Set.copyOf(roles) : Collections.emptySet();
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
