package com.soni.reservation.controller;

import com.soni.reservation.dto.MemberDto;
import com.soni.reservation.dto.ReserveDto;
import com.soni.reservation.dto.ReviewDto;
import com.soni.reservation.service.MemberService;
import io.swagger.annotations.ApiOperation;
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

    /**
     * 이용자 회원가입
     */
    @PostMapping("/register")
    @ApiOperation(value = "이용자 회원가입 API", notes = "정보들을 입력해 회원가입")
    public ResponseEntity<?> register(
            @RequestBody @Valid MemberDto.RegisterRequest request) {

        return ResponseEntity.ok(memberService.register(request));
    }

    /**
     * 이용자 로그인
     */
    @PostMapping("/login")
    @ApiOperation(value = "이용자 로그인 API", notes = "정보들을 입력해 로그인")
    public ResponseEntity<?> login(
            @RequestBody @Valid MemberDto.LoginRequest request) {

        return ResponseEntity.ok(memberService.authenticate(request));
    }

    /**
     * 매장 예약
     */
    @PostMapping("/store/reserve")
    @PreAuthorize("hasRole('MEMBER')")
    @ApiOperation(value = "매장예약 API", notes = "정보들을 입력해 매장 예약")
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
    @ApiOperation(value = "리뷰작성 API", notes = "방문한 매장에 대해 리뷰 작성")
    public ResponseEntity<?> addReview(
            @RequestHeader("Authorization") String token,
            @RequestBody ReviewDto.Request request
    ) {
        return ResponseEntity.ok(memberService.addReview(request, token));
    }
}
