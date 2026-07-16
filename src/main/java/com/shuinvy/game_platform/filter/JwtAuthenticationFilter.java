package com.shuinvy.game_platform.filter;

import com.shuinvy.game_platform.model.User;
import com.shuinvy.game_platform.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> EXCLUDED_PATHS = List.of("/login");

    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null ||
                !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = authorizationHeader.substring(BEARER_PREFIX.length());
        Claims claims;
        try {
            claims = jwtService.parseToken(jwt);
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User user = new User();
        user.setId(claims.get("user_id", Integer.class));
        user.setUsername(claims.get("username", String.class));
        user.setRoleId(claims.get("role_id", Integer.class));

        Authentication token = new UsernamePasswordAuthenticationToken(
                user,
                null,
                List.of()
        );
        SecurityContextHolder.getContext().setAuthentication(token);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return EXCLUDED_PATHS.contains(request.getServletPath());
    }
}
