package com.prac.practice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/create").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // ✅ This is the encoder Spring will use to check passwords
    @Bean
    public PasswordEncoder passwordLocker() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // ✅ THIS is the important part: wire UserDetailsService + PasswordEncoder
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            CustomUserDetailsService customUserDetailsService,
            PasswordEncoder passwordEncoder
    ) throws Exception {

        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        builder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);

        return builder.build();
    }
}
