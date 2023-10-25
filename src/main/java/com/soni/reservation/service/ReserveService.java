package com.soni.reservation.service;

import com.soni.reservation.domain.Reserve;
import com.soni.reservation.dto.ReserveDto;
import com.soni.reservation.dto.StoreDto;
import com.soni.reservation.repository.MemberRepository;
import com.soni.reservation.repository.ReserveRepository;
import com.soni.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReserveService {
    private final MemberRepository memberRepository;
    private final ReserveRepository reserveRepository;
    private final StoreRepository storeRepository;

    public ReserveDto addReserve(Long memberId, ReserveDto reserve) {
        var member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("가입되지 않은 회원입니다."));

        var store = storeRepository.findByStoreName(reserve.getStoreName())
                .orElseThrow(() -> new RuntimeException("매장이 없습니다."));

        if (reserveRepository.countByReservedAt(reserve.getReservedAt()) == 5) {
            throw new RuntimeException("해당 시간에 예약이 다 찼습니다.");
        }

        Reserve save = reserveRepository.save(
                Reserve.builder()
                        .reservedAt(reserve.getReservedAt())
                        .member(member)
                        .store(store)
                        .build()
        );

        return ReserveDto.builder()
                .storeName(reserve.getStoreName())
                .reservedAt(reserve.getReservedAt())
                .build();
    }
}
