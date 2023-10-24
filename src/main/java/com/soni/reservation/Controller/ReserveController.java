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

    //회원가입 된 사람만 가능 ! 인데 회원 정보를 ....
    @PostMapping("/{memberId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> reserveStore(
            @PathVariable Long memberId,
            @RequestBody ReserveDto request) {

        reserveService.addReserve(memberId, request);
        return null;

    }
}
