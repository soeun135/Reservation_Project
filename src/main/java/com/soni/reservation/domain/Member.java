package com.soni.reservation.domain;

import com.soni.reservation.dto.MemberDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String mail;
    private String password;

    private String role;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "reserve_id")
    private Reserve reserve;

    public static MemberDto.RegisterResponse toResponse(Member member) {
        return MemberDto.RegisterResponse.builder()
                .mail(member.getMail())
                .registeredAt(member.getCreatedAt())
                .build();
    }

}
