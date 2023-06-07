package com.example.airbnbApi.config;

import com.example.airbnbApi.config.exception.AccessTokenException;
import com.nimbusds.jose.shaded.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal (
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull  FilterChain filterChain) throws ServletException, IOException, ExpiredJwtException, MalformedJwtException {
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }



        try {
            String authHeader = request.getHeader("Authorization");
            String jwt = authHeader.substring(7);
            String userEmail = jwtService.extractUsername(jwt);
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                filterChain.doFilter(request,response);
                return;
            }
            if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if(jwtService.isTokenValid(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        }catch (ExpiredJwtException e){
            request.setAttribute("exception", HttpStatus.UNAUTHORIZED);
            request.setAttribute("msg", "토큰 만료");
        }catch (MalformedJwtException e){
            request.setAttribute("exception", HttpStatus.UNAUTHORIZED);
            request.setAttribute("msg", "손상된 토큰 에러");
        }catch (UnsupportedJwtException e){
            request.setAttribute("exception", HttpStatus.UNAUTHORIZED);
            request.setAttribute("msg", "지원하지 않는 토큰 에러");
        } catch (Exception e){
            request.setAttribute("exception", HttpStatus.UNAUTHORIZED);
            request.setAttribute("msg", "에러");
        }




        filterChain.doFilter(request,response);


    }
}
