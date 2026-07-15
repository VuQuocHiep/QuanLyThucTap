package com.Hiep.B23DCCN295.config;

import javax.crypto.spec.SecretKeySpec;

import jakarta.persistence.Embeddable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    protected static final String SIGNER_KEY = "kF3mW8zQ2YpN7aBcX5tLvR9HsJ4dNeUgPi6rTx0KwEfVqZmCn81oIyAuDbGhLsR0";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeHttpRequestsCustomizer ->
            authorizeHttpRequestsCustomizer
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST,"/auth/introspect").permitAll()
                .anyRequest().authenticated()
        );
        http.csrf(csrfCustomizer -> csrfCustomizer.disable());

        http.oauth2ResourceServer(oauth2 ->
            oauth2.jwt(jwt -> {
                jwt.decoder(jwtDecoder());
            })
        );

        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(),"HmacSHA256");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }
}
