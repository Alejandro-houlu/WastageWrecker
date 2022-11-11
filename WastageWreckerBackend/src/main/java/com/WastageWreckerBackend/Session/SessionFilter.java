package com.WastageWreckerBackend.Session;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import com.WastageWreckerBackend.Services.UserDetailsServiceImplementation;

@Component
public class SessionFilter extends OncePerRequestFilter {

    @Autowired
    private SessionRegistry sessionRegistry;
    
    @Autowired
    private UserDetailsServiceImplementation userDetailsSvc;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String sessionId = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(sessionId == null || sessionId.length() == 0){
            filterChain.doFilter(request, response);
            return;
        }

        String username = sessionRegistry.getUsernameForSession(sessionId);
        if(username == null){
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails user = userDetailsSvc.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            user,
            null,
            user.getAuthorities()
            );
        
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);


        
    }

    
    
}
