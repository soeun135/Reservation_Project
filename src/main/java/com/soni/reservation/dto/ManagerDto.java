package com.soni.reservation.dto;

import com.soni.reservation.domain.Manager;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class ManagerDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterRequest {
        @NotBlank
        private String name;
        @NotBlank
        private String mail;
        @NotBlank
        private String password;

        private String role;

        public Manager toEntity() {
            return Manager.builder()
                    .name(this.getName())
                    .password(this.getPassword())
                    .mail(this.getMail())
                    .role(this.getRole())
                    .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RegisterResponse {
        private String mail;
        private LocalDateTime registeredAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        @NotBlank
        private String mail;
        @NotBlank
        private String password;
    }
}
