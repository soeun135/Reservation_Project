package com.soni.reservation.Controller;

import com.soni.reservation.domain.Manager;
import com.soni.reservation.dto.ManagerDto;
import com.soni.reservation.dto.StoreDto;
import com.soni.reservation.service.ManageService;
import com.soni.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManageController {
    private final ManageService manageService;
    private final StoreService storeService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid ManagerDto.RegisterRequest request) {
        return ResponseEntity.ok(Manager.toResponse(manageService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid ManagerDto.LoginRequest request) {
        return ResponseEntity.ok(this.manageService.authenticate(request));
    }

    @PostMapping("/store/{managerId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> addStore(@RequestBody @Valid StoreDto.AddStoreRequest store,
                                      @PathVariable Long managerId) {
        return ResponseEntity.ok(storeService.addStore(store, managerId));
    }

    @GetMapping("/searchStore/{managerId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> searchStore(
            @PathVariable Long managerId
    ) {
        return ResponseEntity.ok(manageService.searchStore(managerId));
    }

    @GetMapping("/searchReserve/{storeId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> searchReserve(
            @PathVariable Long storeId
    ) {
        return ResponseEntity.ok(manageService.searchReserve(storeId));
    }

    @PatchMapping("/reserveConfirm/{reserveId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> confirmReserve(
            @PathVariable Long reserveId
    ) {
        manageService.confirmReserve(reserveId);
        return ResponseEntity.ok(reserveId + " 예약을 승인했습니다.");
    }
}
