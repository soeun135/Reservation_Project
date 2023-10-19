package com.soni.reservation.Controller;

import com.soni.reservation.dto.Register;
import com.soni.reservation.service.ManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            @RequestBody Register.Request request)
    {
        return null;
    }
}
