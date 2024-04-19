package com.example.springbootkeycloack.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@RequiredArgsConstructor
@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public static final String ADMIN = "admin";
    public static final String USER = "user";

    private JwtAuthConverter jwtAuthConverter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(http -> http.anyRequest().authenticated())
//                .authorizeHttpRequests(authorizetionHttpRequests -> authorizetionHttpRequests
//                        .requestMatchers(HttpMethod.GET, "test/anonymous", "/test/anonymous/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "test/admin", "/test/admin/**").hasRole(ADMIN)
//                        .requestMatchers(HttpMethod.GET, "test/user").hasAnyRole(ADMIN, USER)
//                        .anyRequest().authenticated()
//                )
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
                .sessionManagement(sessionManagment -> sessionManagment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .csrf(csrf -> csrf.disable())
                .build();

    }
}

