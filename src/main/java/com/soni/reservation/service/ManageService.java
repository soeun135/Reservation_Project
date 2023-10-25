package com.soni.reservation.service;

import com.soni.reservation.domain.Manager;
import com.soni.reservation.domain.Reserve;
import com.soni.reservation.domain.Store;
import com.soni.reservation.dto.ManagerDto;
import com.soni.reservation.exception.ManagerException;
import com.soni.reservation.exception.UserException;
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

import static com.soni.reservation.type.ErrorCode.*;

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
        validate(manager);

        manager.setRole(String.valueOf(Authority.ROLE_MANAGER));
        manager.setPassword(this.passwordEncoder.encode(manager.getPassword()));
        return this.managerRepository.save(manager.toEntity());
    }

    private void validate(ManagerDto.RegisterRequest manager) {
        boolean exists = this.managerRepository.existsByMail(manager.getMail());
        if (exists) {
            throw new UserException(USER_DUPLICATED);
        }
    }

    public String authenticate(ManagerDto.LoginRequest manager) {
        var user = this.managerRepository.findByMail(manager.getMail())
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        if (!this.passwordEncoder.matches(manager.getPassword(), user.getPassword())) {
            throw new UserException(PASSWORD_UNMATCHED);
        }
        return this.tokenProvider.generateToken(user.getMail(), user.getRole());
    }


    public List<Store> searchStore(Long managerId) {
        List<Store> storeList = storeRepository.findByManagerId(managerId)
                .orElseThrow(() -> new ManagerException(STORE_NOT_FOUND));
        return storeList;
    }

    public List<Reserve> searchReserve(Long storeId) {
        return reserveRepository.findByStoreId(storeId)
                .orElseThrow(() -> new ManagerException(RESERVE_NOT_FOUND));
    }

    public void confirmReserve(Long reserveId) {
        Reserve reserve = reserveRepository.findById(reserveId)
                .orElseThrow(() -> new ManagerException(RESERVE_NOT_FOUND));

        reserve.setConfirm(true);
        reserveRepository.save(reserve);
    }
}
