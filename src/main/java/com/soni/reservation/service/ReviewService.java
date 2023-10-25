package com.soni.reservation.service;

import com.soni.reservation.domain.Reserve;
import com.soni.reservation.domain.Review;
import com.soni.reservation.dto.ReviewDto;
import com.soni.reservation.repository.ReserveRepository;
import com.soni.reservation.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReserveRepository reserveRepository;
    private final ReviewRepository reviewRepository;

    public ReviewDto addReview(ReviewDto review, String reserveNum) {
        Reserve reserve = reserveRepository.findByReserveNum(reserveNum)
                .orElseThrow(() -> new RuntimeException("해당 예약번호에 따른 예약이 없습니다."));

        validateVisited(reserve);

        Review savedReview = reviewRepository.save(
                Review.builder()
                        .store(reserve.getStore())
                        .member(reserve.getMember())
                        .text(review.getText())
                        .build()
        );
        return ReviewDto.builder()
                .storeName(savedReview.getStore().getStoreName())
                .memberName(savedReview.getMember().getName())
                .text(savedReview.getText())
                .build();
    }

    private void validateVisited(Reserve reserve) {
        if (!reserve.getVisited()) {
            throw new RuntimeException("방문하지 않은 예약 건에 대해서 리뷰를 작성할 수 없습니다.");
        }
    }
}
