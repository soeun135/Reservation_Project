package com.soni.reservation.Controller;

import com.soni.reservation.dto.ReserveDto;
import com.soni.reservation.service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final ReserveService reserveService;

    @PostMapping("/{memberId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> addReserve(
            @PathVariable Long memberId,
            @RequestBody ReserveDto request) {

        return ResponseEntity.ok(reserveService.addReserve(memberId, request));

    }

    @PostMapping("/confirm/{reserveNum}")
    public ResponseEntity<?> confirmReserve(
            @PathVariable String reserveNum
    ) {
        reserveService.confirmReserve(reserveNum);
        return null;
    }
}
