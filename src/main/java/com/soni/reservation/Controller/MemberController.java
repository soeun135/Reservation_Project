package com.soni.reservation.Controller;

import com.soni.reservation.domain.Member;
import com.soni.reservation.dto.LoginDto;
import com.soni.reservation.dto.MemberDto;
import com.soni.reservation.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid MemberDto.RegisterRequest request) {

        return ResponseEntity.ok(Member.toResponse(memberService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid LoginDto request) {

        return ResponseEntity.ok(memberService.authenticate(request));
    }
}
