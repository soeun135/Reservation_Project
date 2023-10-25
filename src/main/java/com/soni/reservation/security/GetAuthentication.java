package com.soni.reservation.security;

import com.soni.reservation.service.ManageService;
import com.soni.reservation.service.MemberService;
import com.soni.reservation.type.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import static com.soni.reservation.type.Authority.ROLE_MANAGER;

@Component
@RequiredArgsConstructor
public class GetAuthentication {
    private UserDetailsService userDetailsService;
    private final ManageService manageService;
    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    public Authentication getAuthentication(String jwt) {
        String role = tokenProvider.getRole(jwt);

        if (role.equals("ROLE_MANAGER")) {
            userDetailsService = manageService;
        } else {
            userDetailsService = memberService;
        }
        //jwt 토큰으로부터 인증정보를 가져옴
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(tokenProvider.getMail(jwt));

        //스프링에서 지원해주는 토큰 형식으로 바꿔줌.
        //리턴되는 토큰은 사용자 정보와 사용자 권한 정보를 가지게 됨.
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
