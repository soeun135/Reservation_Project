package com.soni.reservation.controller;

import com.soni.reservation.service.KioskService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kiosk")
@RequiredArgsConstructor
public class KioskController {
    private final KioskService reserveService;


    /**
     * 키오스크에서 방문 확인
     */
    @PatchMapping("/confirm/{reserveNum}")
    @ApiOperation(value = "방문확인에 사용되는 API", notes = "키오스크에서 예약번호를 입력해 방문확인")
    @ApiImplicitParam(name = "reserveNum", value = "예약번호", paramType = "path")
    public ResponseEntity<?> confirmReserve(
            @PathVariable String reserveNum
    ) {
        reserveService.confirmReserve(reserveNum);
        return ResponseEntity.ok(reserveService.confirmReserve(reserveNum));
    }
}
