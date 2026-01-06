package org.example.chessplatformbe.config.security;

import org.example.chessplatformbe.config.security.auth.AuthenticationRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*"); // Consider replacing * with explicit headers for production
            }
        };
    }

    @Bean
    @Order(1)
    public SecurityFilterChain publicEndpoints(HttpSecurity http) throws Exception {
        http
                .securityMatcher(SecurityConstants.LOGIN_ENDPOINT, SecurityConstants.LOGOUT_ENDPOINT, SecurityConstants.AUTH_ENDPOINT, SecurityConstants.REGISTER_ENDPOINT)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll());

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain appSecurity(HttpSecurity httpSecurity,
                                           AuthenticationEntryPoint authenticationEntryPoint,
                                           AuthenticationRequestFilter authenticationRequestFilter) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(configurer ->
                        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(registry ->
                        registry
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers(HttpMethod.GET, SecurityConstants.USERS_ENDPOINT).permitAll()
                                .requestMatchers(HttpMethod.PUT, SecurityConstants.USERS_ENDPOINT).hasRole(SecurityConstants.ROLE_ADMIN)
                                .requestMatchers(HttpMethod.DELETE, SecurityConstants.USERS_ENDPOINT).hasRole(SecurityConstants.ROLE_ADMIN)
                                .requestMatchers(HttpMethod.GET, SecurityConstants.ARTICLE_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.POST, SecurityConstants.ARTICLE_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.DELETE, SecurityConstants.ARTICLE_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.PUT, SecurityConstants.ARTICLE_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.GET, SecurityConstants.GET_COMMENTS_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.POST, SecurityConstants.COMMENT_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.DELETE, SecurityConstants.COMMENT_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.PUT, SecurityConstants.COMMENT_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.POST, SecurityConstants.STREAM_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.DELETE, SecurityConstants.STREAM_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.PUT, SecurityConstants.STREAM_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.GET, SecurityConstants.GET_STREAMS_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.GET, SecurityConstants.WEBSOCKET_ENDPOINT).permitAll()
                                .requestMatchers(HttpMethod.GET, SecurityConstants.SPECTATE_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.GET, SecurityConstants.PROFILE_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.GET, SecurityConstants.NEWS_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.GET, SecurityConstants.CHESS_ENDPOINT).hasRole(SecurityConstants.ROLE_ADMIN)
                                .requestMatchers(HttpMethod.GET, SecurityConstants.REPORT_ENDPOINT).hasRole(SecurityConstants.ROLE_ADMIN)
                                .requestMatchers(HttpMethod.DELETE, SecurityConstants.REPORT_ENDPOINT).hasRole(SecurityConstants.ROLE_ADMIN)
                                .requestMatchers(HttpMethod.POST, SecurityConstants.REPORT_ENDPOINT).authenticated()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(config -> config.authenticationEntryPoint(authenticationEntryPoint))
                .addFilterBefore(authenticationRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
