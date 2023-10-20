package com.soni.reservation.dto;

import com.soni.reservation.domain.Store;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class StoreDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddStoreRequest{
        @NotBlank
        private String storeName;

        private String location;
        private String description;

        private Long managerId;
        public Store toEntity() {
            return Store.builder()
                    .storeName(this.getStoreName())
                    .location(this.getLocation())
                    .description(this.getDescription())
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StoreResponse {
        private String storeName;
        private LocalDateTime createdAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SearchStoreResponse{
        private String storeName;
        private String location;
        private String description;
    }
}
