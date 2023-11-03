package com.soni.reservation.dto;

import lombok.*;


public class ReviewDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Request{
        private String reserveNum;
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
