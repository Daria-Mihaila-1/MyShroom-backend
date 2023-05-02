package com.example.MyShroom_backend.security;

import com.example.MyShroom_backend.exception.JwtAuthenticationException;
import com.example.MyShroom_backend.service.AuthService;
import com.example.MyShroom_backend.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.util.Date;

@Service
@Slf4j
public class JwtTokenService {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);
    @Value("${application.secret}")
    private String jwtSecret;



//    @Value("${application.loginCookieName}")
    private String jwtCookie = "token";

    private AuthService authService;


    public ResponseCookie generateCleanJwtCookie(

    ) {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        return cookie;
    }
//    public String generateToken(Authentication authentication) {
//
//        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
//
//        return Jwts.builder()
//                .setSubject((userPrincipal.getUsername()))
//                .setPayload(userPrincipal.getId().toString())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + 1000000000))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes(StandardCharsets.UTF_8))
//                .compact();
//    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            System.out.println("cookie value is what:"+ cookie.getValue());
            return cookie.getValue();
        }
        return null;
    }


    public String generateTokenFromUsernameAndId(String username, Long id) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .claim("role", "ROLE_USER")
                .claim("id", id)
                .setExpiration(new Date((new Date()).getTime() +  10000000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal, Long id) {
        String jwt = generateTokenFromUsernameAndId(userPrincipal.getUsername(), id);
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(12 * 60 * 60).httpOnly(true).build();
        return cookie;
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
    public void getIdFromToken(String token){
        System.out.println("id ul din token");
        System.out.println(Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody());
    }


//    public String getUserNameFromToken(final String token) throws JwtAuthenticationException {
//        String userName = null;
//        try {
//            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);
//            if (claimsJws != null) {
//                userName = claimsJws.getBody().getSubject();
//            }
//        } catch (final SignatureException | MalformedJwtException | UnsupportedJwtException ex) {
//            log.error("Unsupported jwt token {} with exception {}",
//                    token,
//                    ex.getMessage());
//            throw new JwtAuthenticationException(ex);
//        } catch (final ExpiredJwtException ex) {
//            log.error("Expired jwt token {}",
//                    ex.getMessage());
//            throw new JwtAuthenticationException(ex);
//        } catch (final AuthenticationCredentialsNotFoundException ex) {
//            log.error("An error occured while trying to create authentication based on jwt token, missing credentials {}",
//                    ex.getMessage());
//            throw new JwtAuthenticationException(ex);
//        } catch (final Exception ex) {
//            log.error("Unexpected exception occured while parsing jwt {} exception: {}",
//                    token,
//                    ex.getMessage());
//            throw new JwtAuthenticationException(ex);
//        }
//        return userName;
//    }

}
