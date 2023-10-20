package com.soni.reservation.domain;

import com.soni.reservation.dto.StoreDto;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "store")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeName;
    private String location;
    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "MANAGER_ID")
    private Manager manager;

    public void setManager(Manager manager) {
        this.manager = manager;
        manager.getStores().add(this);
    }

    public static StoreDto.SearchStoreResponse toResponse(Store store) {
        return StoreDto.SearchStoreResponse.builder()
                .storeName(store.getStoreName())
                .location(store.getLocation())
                .description(store.getDescription())
                .build();
    }
}
