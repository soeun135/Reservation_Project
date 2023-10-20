package com.soni.reservation.service;

import com.soni.reservation.domain.Manager;
import com.soni.reservation.dto.Register;
import com.soni.reservation.repository.ManagerRepository;
import com.soni.reservation.type.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManageService {
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    public Manager register(Register.Request manager) {
        boolean exists = this.managerRepository.existsByMail(manager.getMail());
        if (exists) {
            throw new RuntimeException();
        }
        manager.setRole(String.valueOf(Authority.ROLE_MANAGER));
        manager.setPassword(this.passwordEncoder.encode(manager.getPassword()));
        return this.managerRepository.save(manager.toEntity());
    }
}
