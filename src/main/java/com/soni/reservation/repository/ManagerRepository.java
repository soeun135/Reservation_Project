package com.soni.reservation.repository;

import com.soni.reservation.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByMail(String mail);
    boolean existsByMail(String mail);

    boolean existsById(Long id);
}
