package org.example.chessplatformbe.config.security.auth;

import org.example.chessplatformbe.config.security.SecurityConstants;
import org.example.chessplatformbe.config.security.token.AccessToken;
import org.example.chessplatformbe.config.security.token.TokenDecoder;
import org.example.chessplatformbe.config.security.token.exception.InvalidAccessTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

@Component
public class AuthenticationRequestFilter extends OncePerRequestFilter {

    private final TokenDecoder accessTokenDecoder;

    public AuthenticationRequestFilter(TokenDecoder accessTokenDecoder) {
        this.accessTokenDecoder = accessTokenDecoder;
    }

    /**
     * 🔓 Skip authentication for public endpoints
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return path.equals(SecurityConstants.REGISTER_ENDPOINT)
                || path.equals(SecurityConstants.LOGIN_ENDPOINT)
                || path.equals(SecurityConstants.AUTH_ENDPOINT)
                || path.equals(SecurityConstants.LOGOUT_ENDPOINT);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        // If there is no Authorization header or it doesn't start with "Bearer "
        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String accessTokenString = requestTokenHeader.substring(7);

        try {
            // Decode the access token
            AccessToken accessToken = accessTokenDecoder.decode(accessTokenString);

            // Set up security context
            setupSpringSecurityContext(accessToken);

            // Continue the filter chain
            chain.doFilter(request, response);
        } catch (InvalidAccessTokenException e) {
            logger.error("Error validating access token", e);
            sendAuthenticationError(response);
        } catch (AccessDeniedException e) {
            logger.error("Access denied", e);
            sendForbiddenError(response);
        }
    }

    private void sendAuthenticationError(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        response.flushBuffer();
    }

    private void sendForbiddenError(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
        response.flushBuffer();
    }

    private void setupSpringSecurityContext(AccessToken accessToken) {
        String username = accessToken.getSubject();

        // Check the user's role by looking at the roles in the access token
        boolean isAdmin = accessToken.getRoles().contains("Admin");

        if (isAdmin) {
            UserDetails userDetails = new User(
                    username,
                    "",
                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
            authToken.setDetails(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } else {
            UserDetails userDetails = new User(
                    username,
                    "",
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
            authToken.setDetails(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
}

