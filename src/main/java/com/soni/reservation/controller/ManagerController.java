package com.soni.reservation.controller;

import com.soni.reservation.dto.ManagerDto;
import com.soni.reservation.dto.ReserveConfirm;
import com.soni.reservation.dto.StoreDto;
import com.soni.reservation.service.ManagerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService manageService;

    /**
     * 점장 회원가입
     */
    @PostMapping("/register")
    @ApiOperation(value = "점장 회원가입 API", notes = "정보들을 입력해 회원가입")
    public ResponseEntity<?> register(
            @RequestBody @Valid ManagerDto.RegisterRequest request) {
        return ResponseEntity.ok(manageService.register(request));
    }

    /**
     * 점장 로그인
     */
    @PostMapping("/login")
    @ApiOperation(value = "점장 로그인 API", notes = "정보들을 입력해 로그인")
    public ResponseEntity<?> login(@RequestBody @Valid ManagerDto.LoginRequest request) {
        return ResponseEntity.ok(this.manageService.authenticate(request));
    }

    /**
     * 매장 등록
     */
    @PostMapping("/store/add")
    @PreAuthorize("hasRole('MANAGER')")
    @ApiOperation(value = "매장등록 API", notes = "정보들을 입력해 매장등록")
    public ResponseEntity<?> addStore(@RequestBody @Valid StoreDto.AddStoreRequest store,
                                      @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(manageService.addStore(store, token));
    }

    /**
     * 내 매장 찾기
     */
    @GetMapping("/store/search")
    @PreAuthorize("hasRole('MANAGER')")
    @ApiOperation(value = "매장찾기 API", notes = "token을 통해 조회한 점장이 등록한 매장확인")
    public ResponseEntity<?> searchStore(@RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(manageService.searchStore(token));
    }

    /**
     * 매장에 등록된 예약확인
     */
    @GetMapping("/reserve/search/{storeId}")
    @PreAuthorize("hasRole('MANAGER')")
    @ApiOperation(value = "예약확인 API", notes = "조회한 매장에 등록되어있는 예약 확인")
    public ResponseEntity<?> searchReserve(
            @PathVariable Long storeId,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(manageService.searchReserve(storeId, token));
    }

    /**
     * 예약 승인/거절
     */
    @PatchMapping("/reserve/confirm")
    @PreAuthorize("hasRole('MANAGER')")
    @ApiOperation(value = "예약승인/거절 API", notes = "예약에 대해 승인/거절")
    public ResponseEntity<?> confirmReserve(
            @RequestHeader("Authorization") String token,
            @RequestBody ReserveConfirm reserveConfirm
    ) {
        return ResponseEntity.ok(manageService.confirmReserve(reserveConfirm, token)
                + "번 예약을 " + reserveConfirm.isConfirmYn() + " 하였습니다.");
    }
}
