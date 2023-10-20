package com.soni.reservation.Controller;

import com.soni.reservation.domain.Manager;
import com.soni.reservation.dto.Login;
import com.soni.reservation.dto.Register;
import com.soni.reservation.security.TokenProvider;
import com.soni.reservation.service.ManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManageController {
    private final ManageService manageService;

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
}
