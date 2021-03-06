package com.core.covid19.authentication.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.core.covid19.authentication.util.JwtUtil;
import com.core.covid19.services.CustomUserDetailService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String jwtParam = request.getParameter("jwt");

        if (authorizationHeader == null && jwtParam != null)
            authorizationHeader = "Bearer " + jwtParam;

        String email = null;
        String jwt = null;
        
        jwt = jwtUtil.getJwtTokenFromHeader(authorizationHeader);
        
        if(jwtUtil.isValidAuthorizationHeader(authorizationHeader))
        	email = jwtUtil.extractEmail(jwt);
        

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.customUserDetailService.loadUserByUsername(email);
            
            if (jwtUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        
        chain.doFilter(request, response);
    }

}