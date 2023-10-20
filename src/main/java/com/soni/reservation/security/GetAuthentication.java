package com.soni.reservation.security;

import com.soni.reservation.service.ManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAuthentication {
    private final ManageService manageService;
    private final TokenProvider tokenProvider;

    public Authentication getAuthentication(String jwt) {
        //jwt 토큰으로부터 인증정보를 가져옴
        UserDetails userDetails = this.manageService.loadUserByUsername(tokenProvider.getMail(jwt));

        //스프링에서 지원해주는 토큰 형식으로 바꿔줌.
        //리턴되는 토큰은 사용자 정보와 사용자 권한 정보를 가지게 됨.
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
