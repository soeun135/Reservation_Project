package com.soni.reservation.domain;

import com.soni.reservation.dto.Register;
import com.soni.reservation.type.Authority;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "manager")
@EntityListeners(AuditingEntityListener.class)
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MANAGER_ID")
    private Long id;

    private String name;

    private String password;

    private String mail;

    private String role;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "manager",cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Store> stores = new ArrayList<>();

    public static Register.Response toResponse(Manager manager) {
        return Register.Response.builder()
                .mail(manager.getMail())
                .registeredAt(manager.getCreatedAt())
                .build();
    }
}
