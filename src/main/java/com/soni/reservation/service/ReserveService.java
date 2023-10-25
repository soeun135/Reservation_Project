package com.soni.reservation.service;

import com.soni.reservation.domain.Reserve;
import com.soni.reservation.dto.ReserveDto;
import com.soni.reservation.dto.StoreDto;
import com.soni.reservation.exception.ReserveException;
import com.soni.reservation.exception.UserException;
import com.soni.reservation.repository.MemberRepository;
import com.soni.reservation.repository.ReserveRepository;
import com.soni.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.soni.reservation.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReserveService {
    private final MemberRepository memberRepository;
    private final ReserveRepository reserveRepository;
    private final StoreRepository storeRepository;

    public ReserveDto addReserve(Long memberId, ReserveDto reserve) {
        var member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ReserveException(USER_NOT_FOUND));

        var store = storeRepository.findByStoreName(reserve.getStoreName())
                .orElseThrow(() -> new ReserveException(STORE_NOT_FOUND));

        if (reserveRepository.countByReservedAt(reserve.getReservedAt()) == 5) {
            throw new ReserveException(RESERVE_IS_FULL);
        }
        String reserveNum = this.getReserveNum(memberId, reserve);

        Reserve save = reserveRepository.save(
                Reserve.builder()
                        .reservedAt(reserve.getReservedAt())
                        .member(member)
                        .store(store)
                        .reserveNum(reserveNum)
                        .build()
        );
        return ReserveDto.builder()
                .storeName(reserve.getStoreName())
                .reservedAt(reserve.getReservedAt())
                .reserveNum(reserveNum)
                .build();
    }

    private String getReserveNum(Long memberId, ReserveDto reserve) {
        return memberId +
                Integer.toString(reserve.getReservedAt().getYear()) +
                        reserve.getReservedAt().getMonth() +
                        reserve.getReservedAt().getDayOfMonth();
    }

    public void confirmReserve(String reserveNum) {
        Reserve reserve = reserveRepository.findByReserveNum(reserveNum)
                .orElseThrow(() -> new ReserveException(RESERVE_NOT_FOUND));

        validate(reserve);

        reserve.setVisited(true);
        reserveRepository.save(reserve);
    }

    private void validate(Reserve reserved) {
        if (LocalDateTime.now().compareTo(reserved.getReservedAt().plusMinutes(10)) > 0) {
            throw new ReserveException(RESERVE_CANCELED);
        }

        if (!reserved.getConfirm()) {
            throw new ReserveException(RESERVE_NOT_ALLOWED);
        }
    }
}
