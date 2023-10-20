package com.soni.reservation.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

public class Login {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank
        private String mail;
        @NotBlank
        private String password;
    }
}
