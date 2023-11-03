package com.soni.reservation.service;

import com.soni.reservation.domain.Reserve;
import com.soni.reservation.exception.ReserveException;
import com.soni.reservation.repository.ReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.soni.reservation.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReserveService {
    private final ReserveRepository reserveRepository;

    /**
     * 예약 방문 확인
     */
    public void confirmReserve(String reserveNum) {
        Reserve reserve = reserveRepository.findByReserveNum(reserveNum)
                .orElseThrow(() -> new ReserveException(RESERVE_NOT_FOUND));

        validate(reserve);

        reserve.setVisited(true);
        reserveRepository.save(reserve);
    }

    /**
     * 예약 방문 확인 시 유효한지 확인
     */
    private void validate(Reserve reserved) {
        if (LocalDateTime.now().compareTo(reserved.getReservedAt().plusMinutes(10)) > 0) {
            throw new ReserveException(RESERVE_CANCELED);
        }

        if (!reserved.getConfirm()) {
            throw new ReserveException(RESERVE_NOT_ALLOWED);
        }
    }
}
