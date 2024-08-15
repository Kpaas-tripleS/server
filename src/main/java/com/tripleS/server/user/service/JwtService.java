package com.tripleS.server.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.accessToken.expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refreshToken.expiration}")
    private Long refreshTokenExpiration;

    @Value("${jwt.accessToken.header}")
    private String accessTokenHeader;

    @Value("${jwt.refreshToken.header}")
    private String refreshTokenHeader;

    public String createAccessToken(String email) {

        Date now = new Date();

        return JWT.create()
                .withSubject("AccessToken")
                .withExpiresAt(new Date(now.getTime() + accessTokenExpiration))
                .withClaim("email", email)
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String createRefreshToken() {

        Date now = new Date();

        return JWT.create()
                .withSubject("RefreshToken")
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpiration))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public void sendAccessTokenAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(accessTokenHeader, accessToken);
        response.setHeader(refreshTokenHeader, refreshToken);
    }

    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(accessTokenHeader, accessToken);
    }

    public Optional<String> extractRefreshToken(HttpServletResponse request) {
        return Optional.ofNullable(request.getHeader(refreshTokenHeader))
                .filter(refreshtoken -> refreshtoken.startsWith("Bearer "))
                .map(refreshtoken -> refreshtoken.replace("Bearer ", ""));

    }

    public Optional<String> extractAccessToken(HttpServletResponse request) {
        return Optional.ofNullable(request.getHeader(accessTokenHeader))
                .filter(refreshtoken -> refreshtoken.startsWith("Bearer "))
                .map(refreshtoken -> refreshtoken.replace("Bearer ", ""));

    }

    public boolean validateAccessToken(String accessToken) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(accessToken);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 access token: {}", e.getMessage());
            return false;
        }
    }
}
