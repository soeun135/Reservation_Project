package com.soni.reservation.dto;

import com.soni.reservation.domain.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class MemberDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest{
        private String name;
        private String mail;
        private String password;

        private String role;

        public Member toEntity() {
            return Member.builder()
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
    public static class LoginRequest{
        @NotBlank
        private String mail;
        @NotBlank
        private String password;

    }
}
