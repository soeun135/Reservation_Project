package com.soni.reservation.repository;

import com.soni.reservation.domain.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    int countByReservedAt(LocalDateTime reservedAt);
}
