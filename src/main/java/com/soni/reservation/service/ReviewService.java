package com.soni.reservation.service;

import com.soni.reservation.domain.Reserve;
import com.soni.reservation.domain.Review;
import com.soni.reservation.dto.ReviewDto;
import com.soni.reservation.exception.ReviewException;
import com.soni.reservation.repository.ReserveRepository;
import com.soni.reservation.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.soni.reservation.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReserveRepository reserveRepository;
    private final ReviewRepository reviewRepository;

    /**
     * 리뷰 작성
     */
    public ReviewDto.Response addReview(ReviewDto.Request review, String reserveNum) {
        Reserve reserve = reserveRepository.findByReserveNum(reserveNum)
                .orElseThrow(() -> new ReviewException(RESERVE_NOT_FOUND));

        validateVisited(reserve);

        Review savedReview = reviewRepository.save(
                Review.builder()
                        .store(reserve.getStore())
                        .member(reserve.getMember())
                        .text(review.getText())
                        .build()
        );
        return ReviewDto.Response.builder()
                .storeName(savedReview.getStore().getStoreName())
                .memberName(savedReview.getMember().getName())
                .text(savedReview.getText())
                .build();
    }

    /**
     * 리뷰 작성하기 위해 방문했는지 확인
     */
    private void validateVisited(Reserve reserve) {
        if (!reserve.getVisited()) {
            throw new ReviewException(REVIEW_NOT_ALLOWED);
        }
    }
}
