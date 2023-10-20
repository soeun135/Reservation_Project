package com.soni.reservation.repository;

import com.soni.reservation.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    boolean existsByMail(String mail);
}
