package com.soni.reservation.repository;

import com.soni.reservation.domain.Manager;
import com.soni.reservation.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    int countByStoreName(String storeName);

    Optional<Store> findByStoreName(String storeName);

    Optional<List<Store>> findByManagerId(Long id);

    List<Store> findByManager(Manager manager);
}
