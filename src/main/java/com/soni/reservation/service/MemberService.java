package com.soni.reservation.service;

import com.soni.reservation.domain.Member;
import com.soni.reservation.dto.MemberDto;
import com.soni.reservation.repository.MemberRepository;
import com.soni.reservation.type.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
}
