package com.soni.reservation.service;

import com.soni.reservation.domain.Manager;
import com.soni.reservation.domain.Reserve;
import com.soni.reservation.domain.Store;
import com.soni.reservation.dto.ManagerDto;
import com.soni.reservation.repository.ManagerRepository;
import com.soni.reservation.repository.ReserveRepository;
import com.soni.reservation.repository.StoreRepository;
import com.soni.reservation.security.TokenProvider;
import com.soni.reservation.type.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageService implements UserDetailsService {
    private final ManagerRepository managerRepository;
    private final StoreRepository storeRepository;
    private final ReserveRepository reserveRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        return this.managerRepository.findByMail(mail)
                .orElseThrow(RuntimeException::new);
    }
    public Manager register(ManagerDto.RegisterRequest manager) {
        boolean exists = this.managerRepository.existsByMail(manager.getMail());
        if (exists) {
            throw new RuntimeException();
        }
        manager.setRole(String.valueOf(Authority.ROLE_MANAGER));
        manager.setPassword(this.passwordEncoder.encode(manager.getPassword()));
        return this.managerRepository.save(manager.toEntity());
    }

    public String authenticate(ManagerDto.LoginRequest manager) {
        var user = this.managerRepository.findByMail(manager.getMail())
                .orElseThrow(() -> new RuntimeException("해당 회원이 없습니다."));

        if (!this.passwordEncoder.matches(manager.getPassword(), user.getPassword())) {
            throw new RuntimeException("비번 일치 안 함");
        }
        return this.tokenProvider.generateToken(user.getMail(), user.getRole());
    }


    public List<Store> searchStore(Long managerId) {
        List<Store> storeList = storeRepository.findByManagerId(managerId)
                .orElseThrow(() -> new RuntimeException(""));
        return storeList;
    }

    public List<Reserve> searchReserve(Long managerId, Long storeId) {
        return reserveRepository.findByManagerIdAndStoreId(managerId, storeId)
                .orElseThrow(() -> new RuntimeException("예약건이 없습니다."));
    }

    public void confirmReserve(Long reserveId) {
        Reserve reserve = reserveRepository.findById(reserveId)
                .orElseThrow(() -> new RuntimeException("해당 예약건이 없습니다."));

        reserve.setConfirm(true);
        reserveRepository.save(reserve);
    }
}
