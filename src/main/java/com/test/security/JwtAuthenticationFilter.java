package com.test.security;

import org.springframework.web.filter.OncePerRequestFilter;

import com.test.services.MyUserDetailsService;
import com.test.utils.JwtTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final MyUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenutil, MyUserDetailsService userDetailsService)
    {
        this.jwtTokenUtil = jwtTokenutil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        logger.debug("Authorization header: {}");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String usernameExtracted = null;
            try {
                if (token.chars().filter(ch -> ch == '.').count() == 2) {
                    usernameExtracted = jwtTokenUtil.getUsernamefromToken(token);
                }
            } catch (Exception e) {
            }

            if (usernameExtracted != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var userdetails = userDetailsService.loadUserByUsername(usernameExtracted);

                if (jwtTokenUtil.validateToken(token, userdetails)) {
                    UsernamePasswordAuthenticationToken authTok =
                        new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());

                    authTok.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authTok);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
    
}
