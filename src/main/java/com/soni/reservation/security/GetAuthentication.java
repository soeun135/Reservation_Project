package com.soni.reservation.security;

import com.soni.reservation.service.ManagerServiceImpl;
import com.soni.reservation.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAuthentication {
    private UserDetailsService userDetailsService;
    private final ManagerServiceImpl manageService;
    private final MemberServiceImpl memberService;
    private final TokenProvider tokenProvider;

    public Authentication getAuthentication(String jwt) {
        String role = tokenProvider.getRole(jwt);

        if (role.equals("ROLE_MANAGER")) {
            userDetailsService = manageService;
        } else {
            userDetailsService = memberService;
        }
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(tokenProvider.getMail(jwt));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
