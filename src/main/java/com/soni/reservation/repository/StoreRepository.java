package com.soni.reservation.repository;

import com.soni.reservation.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    int countByStoreNameIsGreaterThanEqual(String storeName);

    Optional<Store> findByStoreName(String storeName);
}
