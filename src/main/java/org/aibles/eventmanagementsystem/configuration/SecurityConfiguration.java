package org.aibles.eventmanagementsystem.configuration;

import org.aibles.eventmanagementsystem.error_handle.AuthenticationEntryPointImpl;
import org.aibles.eventmanagementsystem.filter.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final AuthTokenFilter jwtAuthenticationFilter;

    private final AuthenticationEntryPointImpl authenticationErrorHandle;

    public SecurityConfiguration(AuthTokenFilter jwtAuthenticationFilter,
                                 AuthenticationEntryPointImpl authenticationErrorHandle) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationErrorHandle = authenticationErrorHandle;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/v1/signup").permitAll()
                        .requestMatchers("/api/v1/login").permitAll()
                        .requestMatchers("/api/v1/forgot-password").permitAll()
                        .requestMatchers("/api/v1/verify-reset-password-otp").permitAll()
                        .requestMatchers("/api/v1/reset-password").permitAll()
                        .requestMatchers("/api/v1/change-password").authenticated()
                        .requestMatchers("/api/v1/users/account:active").authenticated()
                        .requestMatchers("/api/v1/users/accounts:active-otp").authenticated()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationErrorHandle))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}