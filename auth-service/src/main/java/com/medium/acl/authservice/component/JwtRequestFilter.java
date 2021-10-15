package com.medium.acl.authservice.component;

import com.medium.acl.authservice.service.JwtUtilService;
import com.medium.acl.authservice.service.ParticipantService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final ParticipantService participantService;
    private final JwtUtilService jwtUtilService;

    public JwtRequestFilter(ParticipantService participantService, JwtUtilService jwtUtilService) {
        this.participantService = participantService;
        this.jwtUtilService = jwtUtilService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // TODO: Should have better logging
        final String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String username = null;
        String jwtToken = null;

        if (authorizationHeader != null && !authorizationHeader.isEmpty() && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring("Bearer ".length());
            username = jwtUtilService.extractUsername(jwtToken);
        }

        // TODO: Need to IN-depth knowledge for authentication mechanism in spring-boot
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = participantService.loadUserByUsername(username);
            if (jwtUtilService.validateToken(jwtToken, userDetails)) {
                //TODO: a lot to unpack and understand here :(((
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
