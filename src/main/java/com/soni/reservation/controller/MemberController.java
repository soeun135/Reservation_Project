package com.soni.reservation.controller;

import com.soni.reservation.domain.Member;
import com.soni.reservation.dto.LoginDto;
import com.soni.reservation.dto.MemberDto;
import com.soni.reservation.dto.ReserveDto;
import com.soni.reservation.dto.ReviewDto;
import com.soni.reservation.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberServiceImpl memberService;

    /**
     * 이용자 회원가입
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid MemberDto.RegisterRequest request) {

        return ResponseEntity.ok(Member.toResponse(memberService.register(request)));
    }

    /**
     * 이용자 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid LoginDto request) {

        return ResponseEntity.ok(memberService.authenticate(request));
    }

    /**
     * 매장 예약
     */
    @PostMapping("/store/reserve")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> addReserve(
            @RequestHeader("Authorization") String token,
            @RequestBody ReserveDto request) {

        return ResponseEntity.ok(memberService.addReserve(token, request));

    }

    /**
     * 리뷰작성
     */
    @PostMapping("/store/review")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> addReview(
            @RequestHeader("Authorization") String token,
            @RequestBody ReviewDto.Request request
    ) {
        return ResponseEntity.ok(memberService.addReview(request, token));
    }
}
