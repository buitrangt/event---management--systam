package org.aibles.eventmanagementsystem.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.aibles.eventmanagementsystem.repository.AccountRepository;
import org.aibles.eventmanagementsystem.repository.AccountRoleRepository;
import org.aibles.eventmanagementsystem.service.AuthTokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private final AuthTokenService authTokenService;
    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("Auth token filter initiated for request: {}", request.getRequestURI());

        final String accessToken = request.getHeader("Authorization");
        if (Objects.isNull(accessToken)) {
            log.warn("No Authorization header found in the request.");
            filterChain.doFilter(request, response);
            return;
        }
        log.debug("Access token: {}", accessToken);

        if (!accessToken.startsWith("Bearer ")) {
            log.warn("Authorization header does not start with 'Bearer '.");
            filterChain.doFilter(request, response);
            return;
        }

        var jwtToken = accessToken.substring(7);
        String username;
        try {
            username = authTokenService.getSubjectFromAccessToken(jwtToken);
            log.debug("Extracted username from token: {}", username);
        } catch (Exception ex) {
            log.error("Failed to extract user ID from token", ex);
            filterChain.doFilter(request, response);
            return;
        }

        if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            log.debug("User ID is present and no authentication found in security context");
            var account = accountRepository.findByUsername(username)
                    .orElseThrow(() -> {
                        log.error("Account not found for username: {}", username);
                        return new UsernameNotFoundException("Account not found");
                    });
            if (authTokenService.validateAccessToken(jwtToken, username)) {
                log.debug("Token is valid for username: {}", username);
                List<String> roles = accountRoleRepository.findRoleNamesByUsername(username);
                if (roles.isEmpty()) {
                    log.error("No roles found for the account with username: {}", username);
                    throw new IllegalStateException("No roles found for the account");
                }

                Collection<GrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());

                var usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(
                        account.getUsername(), account.getId(), authorities);
                usernamePasswordAuthToken.setDetails(account);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
                log.info("User authenticated: {}", account.getUsername());
            } else {
                log.warn("Token validation failed for user ID: {}", username);
            }
        }
        filterChain.doFilter(request, response);
    }
}