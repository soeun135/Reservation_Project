package com.soni.reservation.controller;

import com.soni.reservation.service.StoreService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    /**
     * 매장 검색
     */
    @GetMapping("/search/{storeName}")
    @ApiOperation(value = "매장검색 API", notes = "매장 명으로 매장 상세정보 조회")
    @ApiImplicitParam(name = "storeName", value = "매장 명", paramType = "path")
    public ResponseEntity<?> searchStore(
            @PathVariable String storeName) {

        return ResponseEntity.ok(storeService.searchStore(storeName));
    }


}
