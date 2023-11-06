package com.soni.reservation.service;

import com.soni.reservation.dto.LoginDto;
import com.soni.reservation.dto.MemberDto;
import com.soni.reservation.dto.ReserveDto;
import com.soni.reservation.dto.ReviewDto;

public interface MemberService {
    /**
     * 이용자 회원가입
     * - 유효성 검증 : 중복된 이메일일 경우 UserException 발생
     * - Role : ROLE_MEMBER로 set.
     * - 비밀번호 : PasswordEncoder.encode이용 암호화해서 저장.
     */
    MemberDto.RegisterResponse register(MemberDto.RegisterRequest member);

    /**
     * 로그인 유효한지 확인
     * - 유효성 검증 : Member 테이블에 없을 경우 UserException 발생
     *             : 비밀번호 일치하지 않을 경우 UserException 발생
     * - 일치할 경우 TokenProvider에서 토큰 만들어서 반환.
     */
    String authenticate(LoginDto member);

    /**
     * 매장 예약
     * - 유효성 검증 : 회원 존재하지 않으면 예외
     *             : 매장 존재하지 않으면 예외
     *             : 동 시간대에 예약이 5건 이상이면 예외
     *             : 해당 회원 예약 중 같은 매장에 예약내역이 있고 방문되지 않았으면 예외
     *              (한 매장당 예약 한 건만 가능하게 하기 위한 처리)
     */
    ReserveDto addReserve(String token, ReserveDto reserve);

    /**
     * 리뷰 작성
     * - 유효성 검증 : token이 올바르지 않으면 예외
     *             : 예약이 존재하지 않으면 예외
     *             : 해당 예약을 한 회원이 아니면 예외
     *             : 방문여부가 true가 아니면 예외
     */
    ReviewDto.Response addReview(ReviewDto.Request review, String token);
}
