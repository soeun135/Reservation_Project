package com.soni.reservation.controller;

import com.soni.reservation.domain.Member;
import com.soni.reservation.dto.LoginDto;
import com.soni.reservation.dto.MemberDto;
import com.soni.reservation.dto.ReserveDto;
import com.soni.reservation.dto.ReviewDto;
import com.soni.reservation.service.MemberService;
import com.soni.reservation.service.ReserveService;
import com.soni.reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final ReserveService reserveService;
    private final ReviewService reviewService;

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
    @PostMapping("/{memberId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> addReserve(
            @PathVariable Long memberId,
            @RequestBody ReserveDto request) {

        return ResponseEntity.ok(reserveService.addReserve(memberId, request));

    }

    /**
     * 리뷰작성
     */
    @PostMapping("/{reserveNum}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> addReview(
            @RequestBody ReviewDto.Request request,
            @PathVariable String reserveNum
    ) {
        return ResponseEntity.ok(reviewService.addReview(request, reserveNum));
    }
}
