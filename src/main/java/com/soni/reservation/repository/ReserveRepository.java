package com.soni.reservation.repository;

import com.soni.reservation.domain.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    int countByReservedAt(LocalDateTime reservedAt);

    Optional<Reserve> findByReserveNum(String reserveNum);

    Optional<List<Reserve>> findByStoreId(Long storeId);
}
