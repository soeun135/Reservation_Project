package com.soni.reservation.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@Table(name = "manager")
@EntityListeners(AuditingEntityListener.class)
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MANAGER_ID")
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    private String mail;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "manager")
    private List<Store> stores = new ArrayList<>();
}
