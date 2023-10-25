package com.soni.reservation.dto;

import com.soni.reservation.domain.Member;
import com.soni.reservation.domain.Store;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class ReviewDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Request{
        private String text;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Response{
        private String memberName;
        private String storeName;
        private String text;
    }

}
