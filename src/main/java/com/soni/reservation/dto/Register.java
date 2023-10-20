package com.soni.reservation.dto;

import com.soni.reservation.domain.Manager;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class Register {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{
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
    public static class Response{
        private String mail;
        private LocalDateTime registeredAt;
    }
}
