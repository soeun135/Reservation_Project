package com.soni.reservation.controller;

import com.soni.reservation.domain.Store;
import com.soni.reservation.service.StoreServiceImpl;
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
    private final StoreServiceImpl storeService;

    /**
     * 매장 검색
     */
    @GetMapping("/search/{storeName}")
    public ResponseEntity<?> searchStore(
            @PathVariable String storeName) {

        return ResponseEntity.ok(storeService.searchStore(storeName));
    }


}
