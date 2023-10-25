package com.soni.reservation.service;

import com.soni.reservation.domain.Member;
import com.soni.reservation.dto.LoginDto;
import com.soni.reservation.dto.MemberDto;
import com.soni.reservation.exception.UserException;
import com.soni.reservation.repository.MemberRepository;
import com.soni.reservation.security.TokenProvider;
import com.soni.reservation.type.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.soni.reservation.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        return this.memberRepository.findByMail(mail)
                .orElseThrow(RuntimeException::new);
    }

    /**
     * 이용자 회원가입
     */
    public Member register(MemberDto.RegisterRequest member) {
        validate(member);

        member.setRole(String.valueOf(Authority.ROLE_MEMBER));
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member.toEntity());
    }

    /**
     * 회원가입 유효한지 확인
     */
    private void validate(MemberDto.RegisterRequest member) {
        boolean exists = memberRepository.existsByMail(member.getMail());
        if (exists) {
            throw new UserException(USER_DUPLICATED);
        }
    }

    /**
     * 로그인 유효한지 확인
     */
    public String authenticate(LoginDto member) {
        var user = memberRepository.findByMail(member.getMail())
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new UserException(PASSWORD_UNMATCHED);
        }
        return this.tokenProvider.generateToken(member.getMail(), user.getRole());
    }
}
