package com.tripleS.server.user.service;

import com.tripleS.server.global.dto.AuthUser;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.service.type.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    private Key key;
    private static final String ROLE = "role";

    @PostConstruct
    public void setKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    //access token 생성
    public String createAccessToken(User user) {
        long now = (new Date()).getTime();

        Date accessValidity = new Date(now + jwtProperties.accessTokenExpiration());

        log.info("expire: {}", accessValidity);

        return Jwts.builder()
                // 토큰의 발급 시간을 기록
                .setIssuedAt(new Date(now))
                .setExpiration(accessValidity)
                // 토큰을 발급한 주체를 설정
                .setIssuer(jwtProperties.issuer())
                .setSubject(user.getId().toString())
                .addClaims(Map.of(ROLE, user.getRole().name()))
                // 토큰의 JWT 타입 명시
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    //refresh token 생성
    public String createRefreshToken(User user) {
        long now = (new Date()).getTime();

        Date refreshValidity = new Date(now + jwtProperties.refreshTokenExpiration());

        return Jwts.builder()
                // 토큰의 발급 시간을 기록
                .setIssuedAt(new Date(now))
                .setExpiration(refreshValidity)
                // 토큰을 발급한 주체를 설정
                .setIssuer(jwtProperties.issuer())
                .setSubject(user.getId().toString())
                .addClaims(Map.of(ROLE, user.getRole().name()))
                // 토큰이 JWT 타입 명시
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    //토큰 만료 여부
    public boolean validateToken(final String token) {
        try {
            log.info("now date: {}", new Date());
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            log.info("expire date: {}", claims.getBody().getExpiration());
            return claims.getBody().getExpiration().after(new Date());
        } catch (Exception e) {
            log.error("Token validation error: ", e);
            return false;
        }
    }

    //user의 정보를 추출
    public AuthUser getUser(String token) {
        Long userId = Long.parseLong(Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject());
        String role = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get(ROLE).toString();

        return AuthUser.of(userId, Collections.singletonList(role));
    }
}