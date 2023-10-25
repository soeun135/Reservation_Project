package com.soni.reservation.service;

import com.soni.reservation.domain.Manager;
import com.soni.reservation.domain.Store;
import com.soni.reservation.dto.StoreDto;
import com.soni.reservation.exception.StoreException;
import com.soni.reservation.repository.ManagerRepository;
import com.soni.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.soni.reservation.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final ManagerRepository managerRepository;
    private final StoreRepository storeRepository;

    /**
     * 매장 추가
     */
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

    /**
     * 매장 추가시 유효한지 검증
     */
    private void validation(StoreDto.AddStoreRequest store, Long managerId) {
        boolean exists = managerRepository.existsById(managerId);

        if (!exists) {
            throw new StoreException(MANAGER_NOT_FOUND);
        }

        int count = storeRepository.countByStoreName(store.getStoreName());

        if (count > 0) {
            throw new StoreException(STORE_DUPLICATED);
        }
    }

    /**
     * 매장 검색
     */
    public Store searchStore(String storeName) {
        return storeRepository.findByStoreName(storeName)
                .orElseThrow(() -> new StoreException(STORE_NOT_FOUND));
    }
}
