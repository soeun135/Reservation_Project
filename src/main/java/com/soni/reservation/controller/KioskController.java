package com.soni.reservation.controller;

import com.soni.reservation.service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kiosk")
@RequiredArgsConstructor
public class KioskController {
    private final ReserveService reserveService;


    /**
     * 키오스크에서 방문 확인
     */
    @PostMapping("/confirm/{reserveNum}")
    public ResponseEntity<?> confirmReserve(
            @PathVariable String reserveNum
    ) {
        reserveService.confirmReserve(reserveNum);
        return null;
    }
}
