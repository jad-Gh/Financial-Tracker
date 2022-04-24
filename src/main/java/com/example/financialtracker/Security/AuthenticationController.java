package com.example.financialtracker.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.financialtracker.AppUser.AppUserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AppUserService appUserService;

    @PostMapping(path = "/signin")
    public ResponseEntity<Map<String,Object>> handleSignIn(@RequestBody UsernameAndPass usernameAndPass){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usernameAndPass.getUsername(),usernameAndPass.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImp user = (UserDetailsImp) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
                .withClaim("Type","Access Token")
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 100*60*1000))
                .withClaim("Type","Refresh Token")
                .sign(algorithm);
        Map<String,Object> data = new HashMap<>();
        data.put("access_token",accessToken);
        data.put("refresh_token",refreshToken);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping(path="/refresh")
    public ResponseEntity<Map<String,Object>> handleRefresh(HttpServletRequest request){
        String authenticationHeader = request.getHeader("Authorization");
        if (authenticationHeader !=null && authenticationHeader.startsWith("Bearer ")){

                String token = authenticationHeader.substring("Bearer".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                String accessToken = JWT.create()
                        .withSubject(username)
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
                        .withClaim("Type","Access Token")
                        .sign(algorithm);

            Map<String,Object> data = new HashMap<>();
            data.put("access_token",accessToken);

            return ResponseEntity.ok().body(data);
        }else {
            throw new RuntimeException("token is missing");
        }
    }
}

@Data
@AllArgsConstructor
class UsernameAndPass{
    private String username;
    private String password;
}
