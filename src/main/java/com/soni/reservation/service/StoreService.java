package com.soni.reservation.service;

import com.soni.reservation.dto.StoreDto;

public interface StoreService {

    /**
     * 매장 검색
     * - 유효성 검증 : 해당 매장이름으로 저장된 매장 없을 시 StoreException 발생
     */
    StoreDto.SearchStoreResponse searchStore(String storeName);
}
