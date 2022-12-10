package com.example.myshroombackend.security;

import com.example.myshroombackend.exception.JwtAuthenticationException;
import com.example.myshroombackend.service.AuthServiceImpl;
import com.example.myshroombackend.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.WebUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class JwtTokenService {
    @Value("${application.secret")
    private String jwtSecret;

    @Value("${application.jwtCookieName}")
    private String jwtCookie;

    private AuthServiceImpl authService;

    public String generateToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 1000000000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public boolean validateToken(String token) {

        if ((token != null) && (!"".equals(token))) {

            Jws<Claims> claimsJws;
            try{
            claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            if (claimsJws != null) {
                final String userId = Optional
                        .ofNullable(claimsJws.getBody().get(new String("id")))
                        .map(Object::toString)//
                        .orElseThrow(
                                () -> new AuthenticationCredentialsNotFoundException("No username given in jwt"));
            }
            return true;
        } catch (ExpiredJwtException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedJwtException e) {
                throw new RuntimeException(e);
            } catch (MalformedJwtException e) {
                throw new RuntimeException(e);
            } catch (SignatureException e) {
                throw new RuntimeException(e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } catch (AuthenticationCredentialsNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }



    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }


    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() +  10000000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/auth").maxAge(24 * 60 * 60).httpOnly(true).build();
        return cookie;
    }


    public String getUserNameFromToken(final String token) throws JwtAuthenticationException {
        String userName = null;
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);
            if (claimsJws != null) {
                userName = claimsJws.getBody().getSubject();
            }
        } catch (final SignatureException | MalformedJwtException | UnsupportedJwtException ex) {
            log.error("Unsupported jwt token {} with exception {}",
                    token,
                    ex.getMessage());
            throw new JwtAuthenticationException(ex);
        } catch (final ExpiredJwtException ex) {
            log.error("Expired jwt token {}",
                    ex.getMessage());
            throw new JwtAuthenticationException(ex);
        } catch (final AuthenticationCredentialsNotFoundException ex) {
            log.error("An error occured while trying to create authentication based on jwt token, missing credentials {}",
                    ex.getMessage());
            throw new JwtAuthenticationException(ex);
        } catch (final Exception ex) {
            log.error("Unexpected exception occured while parsing jwt {} exception: {}",
                    token,
                    ex.getMessage());
            throw new JwtAuthenticationException(ex);
        }
        return userName;
    }

}