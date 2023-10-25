package com.soni.reservation.Controller;


import com.soni.reservation.dto.ReviewDto;
import com.soni.reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{reserveNum}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> addReview(
            @RequestBody ReviewDto.Request request,
            @PathVariable String reserveNum
            ) {
        return ResponseEntity.ok(reviewService.addReview(request, reserveNum));
    }
}
