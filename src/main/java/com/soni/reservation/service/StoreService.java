package com.soni.reservation.service;

import com.soni.reservation.domain.Manager;
import com.soni.reservation.domain.Store;
import com.soni.reservation.dto.StoreDto;
import com.soni.reservation.repository.ManagerRepository;
import com.soni.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final ManagerRepository managerRepository;
    private final StoreRepository storeRepository;
    public StoreDto.AddStoreResponse addStore(StoreDto.AddStoreRequest store, Long managerId) {

        validation(store, managerId);

        Manager manager = managerRepository.findById(managerId).get();

        Store storeEntity = store.toEntity();
        storeEntity.setManager(manager);

        var result = storeRepository.save(storeEntity);
        return StoreDto.AddStoreResponse.builder()
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
}
