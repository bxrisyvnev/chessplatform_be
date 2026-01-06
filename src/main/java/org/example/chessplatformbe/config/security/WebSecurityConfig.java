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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@Configuration
public class WebSecurityConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("https://chessplatform.org")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationEntryPoint authenticationEntryPoint,
            AuthenticationRequestFilter authenticationRequestFilter
    ) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .sessionManagement(sm ->
                sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth
                // ✅ PUBLIC ENDPOINTS
                .requestMatchers(HttpMethod.POST,
                        SecurityConstants.REGISTER_ENDPOINT,
                        SecurityConstants.LOGIN_ENDPOINT
                ).permitAll()

                .requestMatchers(SecurityConstants.AUTH_ENDPOINT).permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ✅ ROLE / AUTH PROTECTED
                .requestMatchers(HttpMethod.GET, SecurityConstants.USERS_ENDPOINT).permitAll()
                .requestMatchers(HttpMethod.PUT, SecurityConstants.USERS_ENDPOINT).hasRole(SecurityConstants.ROLE_ADMIN)
                .requestMatchers(HttpMethod.DELETE, SecurityConstants.USERS_ENDPOINT).hasRole(SecurityConstants.ROLE_ADMIN)

                .requestMatchers(SecurityConstants.ARTICLE_ENDPOINT).authenticated()
                .requestMatchers(SecurityConstants.COMMENT_ENDPOINT).authenticated()
                .requestMatchers(SecurityConstants.GET_COMMENTS_ENDPOINT).authenticated()
                .requestMatchers(SecurityConstants.STREAM_ENDPOINT).authenticated()
                .requestMatchers(SecurityConstants.GET_STREAMS_ENDPOINT).authenticated()
                .requestMatchers(SecurityConstants.SPECTATE_ENDPOINT).authenticated()
                .requestMatchers(SecurityConstants.PROFILE_ENDPOINT).authenticated()
                .requestMatchers(SecurityConstants.NEWS_ENDPOINT).authenticated()

                .requestMatchers(SecurityConstants.CHESS_ENDPOINT).hasRole(SecurityConstants.ROLE_ADMIN)
                .requestMatchers(SecurityConstants.REPORT_ENDPOINT).hasRole(SecurityConstants.ROLE_ADMIN)

                // ✅ EVERYTHING ELSE
                .anyRequest().authenticated()
            )

            .exceptionHandling(eh ->
                eh.authenticationEntryPoint(authenticationEntryPoint)
            )

            .addFilterBefore(
                authenticationRequestFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}

