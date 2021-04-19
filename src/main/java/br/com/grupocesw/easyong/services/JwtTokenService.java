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

    public String generateToken(Authentication authentication);

    public Claims getClaimsFromJWT(String token);
    
    public Claims getAllClaimsFromToken(String token);
    
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);
    
    public Date getExpirationDateFromToken(String token);
    
    public Boolean isTokenExpired(String token);

    public boolean validateToken(String authToken);

    abstract void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException;
}
