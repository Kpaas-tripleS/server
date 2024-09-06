package com.tripleS.server.global.util;

import com.tripleS.server.global.dto.AuthUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationUtil {

    public static void makeAuthentication(AuthUser authUser) {
        // ContextHolder 에 Authentication 정보 저장
        Authentication auth = AuthenticationUtil.getAuthentication(authUser);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private static Authentication getAuthentication(AuthUser authUser) {
        List<GrantedAuthority> grantedAuthorities = authUser.roles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(authUser, "", grantedAuthorities);
    }
}