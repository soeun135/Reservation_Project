package com.soni.reservation.service;

public interface KioskService {

    /**
     * 예약 방문 확인
     * - 유효성 검증 : 없는 예약일 경우 ReserveException 발생.
     */
    void confirmReserve(String reserveNum);
}
