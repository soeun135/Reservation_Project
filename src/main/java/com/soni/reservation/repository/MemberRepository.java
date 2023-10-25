package com.soni.reservation.repository;

import com.soni.reservation.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);

    Optional<Member> findByMail(String mail);
    boolean existsByMail(String mail);
}
