package com.soni.reservation.Controller;


import com.soni.reservation.dto.ReviewDto;
import com.soni.reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{reserveNum}")
    public ResponseEntity<?> addReview(
            @RequestBody ReviewDto.Request request,
            @PathVariable String reserveNum
            ) {
        return ResponseEntity.ok(reviewService.addReview(request, reserveNum));
    }
}
