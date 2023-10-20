package com.soni.reservation.domain;

import com.soni.reservation.dto.ManagerDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
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
public class Manager implements UserDetails {

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

    public static ManagerDto.RegisterResponse toResponse(Manager manager) {
        return ManagerDto.RegisterResponse.builder()
                .mail(manager.getMail())
                .registeredAt(manager.getCreatedAt())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.role);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return authorities;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
