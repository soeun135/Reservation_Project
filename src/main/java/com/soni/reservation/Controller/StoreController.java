package com.soni.reservation.Controller;

import com.soni.reservation.domain.Store;
import com.soni.reservation.dto.StoreDto;
import com.soni.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/search/{storeName}")
    public ResponseEntity<?> searchStore(
            @PathVariable String storeName) {

        return ResponseEntity.ok(Store.toResponse(storeService.searchStore(storeName)));
    }
}
