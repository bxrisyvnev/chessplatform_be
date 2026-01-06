package org.example.chessplatformbe.config.security;

import org.example.chessplatformbe.config.security.auth.AuthenticationRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationEntryPoint authenticationEntryPoint,
            AuthenticationRequestFilter authenticationRequestFilter
    ) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(registry -> registry

                // ✅ PUBLIC ENDPOINTS
                .requestMatchers(HttpMethod.POST, SecurityConstants.REGISTER_ENDPOINT).permitAll()
                .requestMatchers(HttpMethod.POST, SecurityConstants.LOGIN_ENDPOINT).permitAll()
                .requestMatchers(SecurityConstants.AUTH_ENDPOINT).permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ✅ ADMIN
                .requestMatchers(HttpMethod.PUT, SecurityConstants.USERS_ENDPOINT).hasRole(SecurityConstants.ROLE_ADMIN)
                .requestMatchers(HttpMethod.DELETE, SecurityConstants.USERS_ENDPOINT).hasRole(SecurityConstants.ROLE_ADMIN)
                .requestMatchers(HttpMethod.GET, SecurityConstants.CHESS_ENDPOINT).hasRole(SecurityConstants.ROLE_ADMIN)
                .requestMatchers(HttpMethod.GET, SecurityConstants.REPORT_ENDPOINT).hasRole(SecurityConstants.ROLE_ADMIN)
                .requestMatchers(HttpMethod.DELETE, SecurityConstants.REPORT_ENDPOINT).hasRole(SecurityConstants.ROLE_ADMIN)

                // ✅ AUTHENTICATED USERS
                .requestMatchers(HttpMethod.GET, SecurityConstants.ARTICLE_ENDPOINT).authenticated()
                .requestMatchers(HttpMethod.POST, SecurityConstants.ARTICLE_ENDPOINT).authenticated()
                .requestMatchers(HttpMethod.PUT, SecurityConstants.ARTICLE_ENDPOINT).authenticated()
                .requestMatchers(HttpMethod.DELETE, SecurityConstants.ARTICLE_ENDPOINT).authenticated()

                .requestMatchers(HttpMethod.GET, SecurityConstants.GET_COMMENTS_ENDPOINT).authenticated()
                .requestMatchers(HttpMethod.POST, SecurityConstants.COMMENT_ENDPOINT).authenticated()
                .requestMatchers(HttpMethod.PUT, SecurityConstants.COMMENT_ENDPOINT).authenticated()
                .requestMatchers(HttpMethod.DELETE, SecurityConstants.COMMENT_ENDPOINT).authenticated()

                .requestMatchers(HttpMethod.POST, SecurityConstants.STREAM_ENDPOINT).authenticated()
                .requestMatchers(HttpMethod.PUT, SecurityConstants.STREAM_ENDPOINT).authenticated()
                .requestMatchers(HttpMethod.DELETE, SecurityConstants.STREAM_ENDPOINT).authenticated()
                .requestMatchers(HttpMethod.GET, SecurityConstants.GET_STREAMS_ENDPOINT).authenticated()

                .requestMatchers(HttpMethod.GET, SecurityConstants.SPECTATE_ENDPOINT).authenticated()
                .requestMatchers(HttpMethod.GET, SecurityConstants.PROFILE_ENDPOINT).authenticated()
                .requestMatchers(HttpMethod.GET, SecurityConstants.NEWS_ENDPOINT).authenticated()

                .requestMatchers(SecurityConstants.WEBSOCKET_ENDPOINT).permitAll()

                // ❌ EVERYTHING ELSE
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex ->
                ex.authenticationEntryPoint(authenticationEntryPoint)
            )
            .addFilterBefore(authenticationRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

