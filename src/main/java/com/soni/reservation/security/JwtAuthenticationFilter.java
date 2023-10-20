package com.soni.reservation.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {//한 요청당 한 번씩 필터를 거침(컨트롤러 전에)
    //요청이 들어올 때마다 토큰이 포함되어있는지 확인하고 토큰이 유효한지 확인
    public static final String TOKEN_HEADER = "Authorization"; //토큰은 헤더에 포함되어있어서 그때의 key로 쓸 문자열을 정해줌.
    public static final String TOKEN_PREFIX = "Bearer ";//인증타입을 나타내기 위해 쓰는데 JWT 토큰은 앞에 Bearer를 붙임.

    private final TokenProvider tokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 1. request의 header에서 토큰을 꺼내옴
        String token = this.resolveTokenFromRequest(request);

        // 2. token이 유효한지 검증
        if (StringUtils.hasText(token) && this.tokenProvider.validateToken(token)) {
            //토큰 유효성 검증 완료
            // Spring Security에 인증정보를 넣어주기 위해 TokenProvider에 getAuthentication 메소드 추가
            Authentication auth = this.tokenProvider.getAuthentication(token);
            //Authentication은 현재 접근하는 주체의 정보와 권한을 담는 인터페이스.
            //Authentication 객체는 Security Context에 저장되며
            //SecurityContextHolder 통해 SecurityContext에 접근,
            //SecurityContext통해 Aythentication에 접근

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        //스프링의 필터에는 필터체인이라는 개념이 있음. 필터가 연속적으로 실행될 수 있도록
        filterChain.doFilter(request, response);
    }

    private String resolveTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);

        if (!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            return token.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
