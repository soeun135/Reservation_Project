package com.soni.reservation.service;

import com.soni.reservation.domain.Manager;
import com.soni.reservation.domain.Reserve;
import com.soni.reservation.domain.Store;
import com.soni.reservation.dto.ManagerDto;
import com.soni.reservation.dto.ReserveConfirm;
import com.soni.reservation.dto.StoreDto;

import java.util.List;

public interface ManagerService {
    /**
     * 점장 회원가입
     * - 유효성 검증 : 중복된 이메일일 경우 UserException 발생
     * - Role : ROLE_MANAGER로 set.
     * - 비밀번호 : PasswordEncoder.encode이용 암호화해서 저장.
     */
    ManagerDto.RegisterResponse register(ManagerDto.RegisterRequest manager);

    /**
     * 로그인 유효한지 확인
     * - 유효성 검증 : Manager 테이블에 없을 경우 ManagerException 발생
     *             : 비밀번호 일치하지 않을 경우 ManagerException 발생
     * - 일치할 경우 TokenProvider에서 토큰 만들어서 반환.
     */
    String authenticate(ManagerDto.LoginRequest manager);

    /**
     * 매장 추가
     * - private 메소드 getManagerEntity를 이용해서 토큰 값에서 manager entity를 받아옴.
     * - 유효성 검증 : 같은 이름의 매장이 1개 이상이면 StoreException 발생.
     * - store entity에 user를 세팅하고 저장.
     */
    StoreDto.StoreResponse addStore(StoreDto.AddStoreRequest store, String token);

    /**
     * 해당 점장 기준 등록된 매장 확인
     * - 점장을 기준으로 해당 점장이 등록한 매장 정보 확인 메소드
     */
    List<Store> searchStore(String token);

    /**
     * 해당 매장 기준 등록된 예약 확인
     * - 매장을 기준으로 해당 매장에 등록된 예약 정보 확인 메소드
     * - 유효성 검증 : 해당 매장이 없을 경우 ManagerException 발생.
     *    : 예약을 확인하려는 manager와 매장 정보에 등록된 manager가 다를 경우 ManagerException 발생.
     *    : 예약 정보가 없는 경우 ManagerException 발생.
     */
    List<Reserve> searchReserve(Long storeId, String token);

    /**
     * 예약 승인/거절
     * - 유효성 검증 : 매장이 없으면 예외 발생
     *             : 예약이 없으면 예외 발생
     *             : 해당 매장의 예약이 아니면 예외 발생
     *             : 등록하려는 사람의 매장에서 발생한 예약인지 확인
     */
    Long confirmReserve(ReserveConfirm reserveConfirm, String token);
}
