package com.soni.reservation.security;

import com.soni.reservation.service.ManageService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1시간
    private static final String KEY_ROLE = "role";

    @Value("${spring.jwt.secret}")
    private String secretKey;

    public String generateToken(String mail, String role) { //토큰생성 메소드
        Claims claims = Jwts.claims().setSubject(mail); //사용자의 권한 정보를 저장하기 위한 Claims
        claims.put(KEY_ROLE, role); //claims에 정보를 저장할 때는 key-value 형태로 저장

        var now = new Date(); //토큰이 생성된 시간  =현재시간
        var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME); //토큰 생성 시간부터 한 시간동안 유효

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, this.secretKey)//사용할 암호화 알고리즘, 비밀키로 서명
                .compact();
    }


    //토큰이 유효한지 검증하는 메소드
    //만들기 전에 토큰으로부터 Claims 가져오는 메소드 먼저 구현
    public String getRole(String token) {
        return (String) this.parseClaims(token).get(KEY_ROLE);
    }
    public String getMail(String token) {
        return this.parseClaims(token).getSubject();
    }
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) return false; //토큰이 빈 값이면 false

        var claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date()); //토큰이 빈 값이 아니면 유효시간이 만료되었는지 여부를 리턴
    }
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(this.secretKey)
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) { //토큰이 만료됐을 떄 토큰 파싱하려고 할 떄 나는 예외처리
            return e.getClaims();
        }
    }

}
