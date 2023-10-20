package com.soni.reservation.service;

import com.soni.reservation.domain.Manager;
import com.soni.reservation.domain.Store;
import com.soni.reservation.dto.StoreDto;
import com.soni.reservation.repository.ManagerRepository;
import com.soni.reservation.repository.MemberRepository;
import com.soni.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final ManagerRepository managerRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    public StoreDto.StoreResponse addStore(StoreDto.AddStoreRequest store, Long managerId) {

        validation(store, managerId);

        Manager manager = managerRepository.findById(managerId).get();

        Store storeEntity = store.toEntity();
        storeEntity.setManager(manager);

        var result = storeRepository.save(storeEntity);
        return StoreDto.StoreResponse.builder()
                .storeName(result.getStoreName())
                .createdAt(result.getCreatedAt())
                .build();
    }

    private void validation(StoreDto.AddStoreRequest store, Long managerId) {
        boolean exists = managerRepository.existsById(managerId);

        if (!exists) {
            throw new RuntimeException("존재하지 않는 점장입니다.");
        }

        int count = storeRepository.countByStoreNameIsGreaterThanEqual(store.getStoreName());

        if (count > 0) {
            throw new RuntimeException("중복된 매장 명입니다.");
        }
    }

    public Store searchStore(String storeName) {
        return storeRepository.findByStoreName(storeName)
                .orElseThrow(() -> new RuntimeException("없는 매장입니다."));
    }

    public StoreDto.StoreResponse addReserve(String storeName, Long memberId) {
        var member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("가입되지 않은 회원입니다."));

        var store = storeRepository.findByStoreName(storeName)
                .orElseThrow(() -> new RuntimeException("매장이 없습니다."));

        member.setStore(store);
        memberRepository.save(member);

        return StoreDto.StoreResponse.builder()
                .storeName()
                .createdAt()
                .build();
    }
}
