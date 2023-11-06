package com.soni.reservation.service;

import com.soni.reservation.domain.Store;
import com.soni.reservation.exception.StoreException;
import com.soni.reservation.repository.ManagerRepository;
import com.soni.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.soni.reservation.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService{
    private final ManagerRepository managerRepository;
    private final StoreRepository storeRepository;


    /**
     * 매장 검색
     */
    public Store searchStore(String storeName) {
        return storeRepository.findByStoreName(storeName)
                .orElseThrow(() -> new StoreException(STORE_NOT_FOUND));
    }
}
