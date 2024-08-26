package com.tripleS.server.global.filter;

import com.tripleS.server.global.dto.AuthUser;
import com.tripleS.server.global.util.AuthenticationUtil;
import com.tripleS.server.user.service.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = resolveToken(request);
        String requestURI = request.getRequestURI();

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            AuthUser authUser = jwtTokenProvider.getUser(token);
            AuthenticationUtil.makeAuthentication(authUser);

            log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authUser, requestURI);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}