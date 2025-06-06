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
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
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
                                .requestMatchers(HttpMethod.POST,
                                        SecurityConstants.USERS_ENDPOINT,
                                        SecurityConstants.AUTH_ENDPOINT,
                                        "/login").permitAll()
                                .requestMatchers(HttpMethod.DELETE,
                                        SecurityConstants.USERS_ENDPOINT,
                                        SecurityConstants.AUTH_ENDPOINT).permitAll()
                                .requestMatchers(HttpMethod.GET,
                                        SecurityConstants.USERS_ENDPOINT,
                                        SecurityConstants.AUTH_ENDPOINT).permitAll()
                                .requestMatchers(HttpMethod.GET,
                                        SecurityConstants.USERS_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.POST,
                                        SecurityConstants.USERS_ENDPOINT).hasRole(SecurityConstants.ROLE_ADMIN)
                                .requestMatchers(HttpMethod.PUT,
                                        SecurityConstants.USERS_ENDPOINT).hasRole(SecurityConstants.ROLE_ADMIN)
                                .requestMatchers(HttpMethod.DELETE,
                                        SecurityConstants.USERS_ENDPOINT).hasRole(SecurityConstants.ROLE_ADMIN)
                                .requestMatchers(HttpMethod.GET,
                                        SecurityConstants.ARTICLE_ENDPOINT).permitAll()
                                .requestMatchers(HttpMethod.POST,
                                        SecurityConstants.COMMENT_ENDPOINT).permitAll()
                                .requestMatchers(HttpMethod.GET, "/comments/**").authenticated()
                                .requestMatchers(HttpMethod.POST,
                                        SecurityConstants.STREAM_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.GET, "/streams/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/ws/**").permitAll()
                                .requestMatchers(HttpMethod.GET,
                                        SecurityConstants.SPECTATE_ENDPOINT).authenticated()
                                .requestMatchers(HttpMethod.GET, "/users/profile/**").authenticated()
                                //.requestMatchers(SWAGGER_UI_RESOURCES).permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(configure -> configure.authenticationEntryPoint(authenticationEntryPoint))
                .addFilterBefore(authenticationRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");//change this to not be hard coded
            }
        };
    }
}
