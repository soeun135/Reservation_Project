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
public class KioskServiceImpl implements KioskService{
    private final ReserveRepository reserveRepository;

    /**
     * 예약 방문 확인
     */
    public String confirmReserve(String reserveNum) {
        Reserve reserve = reserveRepository.findByReserveNum(reserveNum)
                .orElseThrow(() -> new ReserveException(RESERVE_NOT_FOUND));

        validate(reserve);

        reserve.setVisited(true);
        Reserve save = reserveRepository.save(reserve);
        return save.getReserveNum() + " 방문확인 되었습니다.";
    }

    /**
     * 예약 방문 확인 시 유효한지 확인
     */
    private void validate(Reserve reserved) {
        if (LocalDateTime.now().isAfter(reserved.getReservedAt().plusMinutes(10))) {
            throw new ReserveException(RESERVE_CANCELED);
        }

        if (!reserved.isConfirm()) {
            throw new ReserveException(RESERVE_NOT_ALLOWED);
        }
    }
}
