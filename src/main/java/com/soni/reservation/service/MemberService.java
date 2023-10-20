package com.soni.reservation.service;

import com.soni.reservation.domain.Member;
import com.soni.reservation.dto.MemberDto;
import com.soni.reservation.repository.MemberRepository;
import com.soni.reservation.security.TokenProvider;
import com.soni.reservation.type.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public Member register(MemberDto.RegisterRequest member) {
        validate(member);

        member.setRole(String.valueOf(Authority.ROLE_USER));
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member.toEntity());
    }

    private void validate(MemberDto.RegisterRequest member) {
        boolean exists = memberRepository.existsByMail(member.getName());
        if (exists) {
            throw new RuntimeException("중복된 회원입니다.");
        }
    }

    public String authenticate(MemberDto.RegisterRequest member) {
        var user = memberRepository.findByName(member.getName())
                .orElseThrow(() -> new RuntimeException("사용자가 없음"));

        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new RuntimeException("비번 일치 안 함");
        }
        return this.tokenProvider.generateToken(member.getMail(), user.getRole());
    }
}
