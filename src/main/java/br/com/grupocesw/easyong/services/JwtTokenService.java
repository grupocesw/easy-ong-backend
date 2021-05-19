package br.com.grupocesw.easyong.services;

import java.io.IOException;
import java.util.Date;
import java.util.function.Function;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;

@Service
public interface JwtTokenService {

    String generateToken(Authentication authentication);

    Claims getClaimsFromJWT(String token);
    
    Claims getAllClaimsFromToken(String token);
    
    <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);
    
    Date getExpirationDateFromToken(String token);
    
    Boolean isTokenExpired(String token);

    boolean validateToken(String authToken);

    void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException;
}
