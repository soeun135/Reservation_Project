package com.soni.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReserveConfirm {
    private Long reserveId;
    private Long storeId;
    private boolean confirmYn;
}
