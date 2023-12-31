package com.soni.reservation.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDto {

        private String storeName;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime reservedAt;

        private String reserveNum;
}
