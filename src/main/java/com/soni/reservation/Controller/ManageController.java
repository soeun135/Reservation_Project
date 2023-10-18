package com.soni.reservation.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/manager")
public class ManageController {

    @PostMapping("/register")
    public ResponseEntity<?> register() {

        return null;
    }
}
