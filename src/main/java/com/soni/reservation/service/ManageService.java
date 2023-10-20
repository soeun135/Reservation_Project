package com.soni.reservation.service;

import com.soni.reservation.domain.Manager;
import com.soni.reservation.dto.Login;
import com.soni.reservation.dto.Register;
import com.soni.reservation.repository.ManagerRepository;
import com.soni.reservation.security.TokenProvider;
import com.soni.reservation.type.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManageService implements UserDetailsService {
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.managerRepository.findByMail(username)
                .orElseThrow(RuntimeException::new);
    }
    public Manager register(Register.Request manager) {
        boolean exists = this.managerRepository.existsByMail(manager.getMail());
        if (exists) {
            throw new RuntimeException();
        }
        manager.setRole(String.valueOf(Authority.ROLE_MANAGER));
        manager.setPassword(this.passwordEncoder.encode(manager.getPassword()));
        return this.managerRepository.save(manager.toEntity());
    }

    public String authenticate(Login.Request manager) {
        var user = this.managerRepository.findByMail(manager.getMail())
                .orElseThrow(() -> new RuntimeException());

        if (!this.passwordEncoder.matches(manager.getPassword(), user.getPassword())) {
            throw new RuntimeException("비번 일치 안 함");
        }
        return this.tokenProvider.generateToken(user.getMail(), user.getRole());
    }
}
