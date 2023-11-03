package com.soni.reservation.service;

import com.soni.reservation.domain.Manager;
import com.soni.reservation.domain.Reserve;
import com.soni.reservation.domain.Store;
import com.soni.reservation.dto.ManagerDto;
import com.soni.reservation.dto.ReserveConfirm;
import com.soni.reservation.dto.StoreDto;
import com.soni.reservation.exception.ManagerException;
import com.soni.reservation.exception.StoreException;
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
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.soni.reservation.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ManagerService implements UserDetailsService {
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

    /**
     * 점장 회원가입
     */
    public Manager register(ManagerDto.RegisterRequest manager) {
        validate(manager);

        manager.setRole(String.valueOf(Authority.ROLE_MANAGER));
        manager.setPassword(this.passwordEncoder.encode(manager.getPassword()));
        return this.managerRepository.save(manager.toEntity());
    }

    /**
     * 회원가입 유효한지 확인
     */
    private void validate(ManagerDto.RegisterRequest manager) {
        boolean exists = this.managerRepository.existsByMail(manager.getMail());
        if (exists) {
            throw new UserException(USER_DUPLICATED);
        }
    }

    /**
     * 로그인 유효한지 확인
     */
    public String authenticate(ManagerDto.LoginRequest manager) {
        var user = this.managerRepository.findByMail(manager.getMail())
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        if (!this.passwordEncoder.matches(manager.getPassword(), user.getPassword())) {
            throw new UserException(PASSWORD_UNMATCHED);
        }
        return this.tokenProvider.generateToken(user.getMail(), user.getRole());
    }

    /**
     * 매장 추가
     */
    public StoreDto.StoreResponse addStore(StoreDto.AddStoreRequest store, String token) {

        Manager manager = getManagerEntity(token);

        int count = storeRepository.countByStoreName(store.getStoreName());
        if (count > 0) {
            throw new StoreException(STORE_DUPLICATED);
        }

        Store storeEntity = store.toEntity();
        storeEntity.setManager(manager);

        var result = storeRepository.save(storeEntity);
        return StoreDto.StoreResponse.builder()
                .storeName(result.getStoreName())
                .createdAt(result.getCreatedAt())
                .build();
    }

    /**
     * email로 해당 점장 Entity를 찾는 메소드
     */
    private Manager getManagerEntity(String token) {
        String mail = getMailFromToken(token);

        Optional<Manager> optionalManager = managerRepository.findByMail(mail);
        if (optionalManager.isEmpty()) {
            throw new ManagerException(MANAGER_NOT_FOUND);
        }

        return optionalManager.get();
    }

    /**
     * 토큰에서 email 꺼내오는 메소드
     */

    private String getMailFromToken(String token) {
        if (!ObjectUtils.isEmpty(token) && token.startsWith("Bearer")) {
            token =  token.substring("Bearer".length());
        }
        return tokenProvider.getMail(token);
    }

    /**
     * 해당 점장 기준 등록된 매장 확인
     */
    public List<Store> searchStore(String token) {
        Manager manager = getManagerEntity(token);
        List<Store> storeList = storeRepository.findByManager(manager);

        return storeList;
    }

    /**
     * 해당 매장 기준 등록된 예약 확인
     */
    public List<Reserve> searchReserve(Long storeId, String token) {
        Manager manager = getManagerEntity(token);

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ManagerException(STORE_NOT_FOUND));


        if (!Objects.equals(manager.getId(), store.getManager().getId())) {
            throw new ManagerException(UNMATCHED_MANAGER_STORE);
        }

        return reserveRepository.findByStoreId(storeId)
                .orElseThrow(() -> new ManagerException(RESERVE_NOT_FOUND));
    }

    /**
     * 예약 승인/거절
     */
    public Long confirmReserve(ReserveConfirm reserveConfirm) {
        //매장 존재 확인
        Store store = storeRepository.findById(reserveConfirm.getStoreId())
                .orElseThrow(() -> new ManagerException(STORE_NOT_FOUND));

        //예약 존재 확인
        Reserve reserve = reserveRepository.findById(reserveConfirm.getReserveId())
                .orElseThrow(() -> new ManagerException(RESERVE_NOT_FOUND));

        //해당 매장의 예약인지 확인
        if (store.getId() != reserve.getStore().getId()) {
            throw new ManagerException(UNMATCHED_STORE_RESERVE);
        }

        reserve.setConfirm(reserveConfirm.isConfirmYn());
        reserveRepository.save(reserve);

        return reserveConfirm.getReserveId();
    }
}
