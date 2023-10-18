package com.soni.reservation.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String storeName;
    @NotNull
    private String location;
    @NotNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "MANAGER_ID")
    private Manager manager;
}
