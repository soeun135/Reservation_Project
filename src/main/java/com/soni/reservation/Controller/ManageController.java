package com.soni.reservation.Controller;

import com.soni.reservation.domain.Manager;
import com.soni.reservation.domain.Store;
import com.soni.reservation.dto.Login;
import com.soni.reservation.dto.Register;
import com.soni.reservation.dto.StoreDto;
import com.soni.reservation.security.TokenProvider;
import com.soni.reservation.service.ManageService;
import com.soni.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManageController {
    private final ManageService manageService;
    private final StoreService storeService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid Register.Request request)
    {
        return ResponseEntity.ok(Manager.toResponse(manageService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid Login.Request request) {
        return ResponseEntity.ok(this.manageService.authenticate(request));
    }

    @PostMapping("/store/{managerId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> addStore(@RequestBody @Valid StoreDto.AddStoreRequest store,
                                      @PathVariable Long managerId) {
        return ResponseEntity.ok(storeService.addStore(store, managerId));
    }
}
