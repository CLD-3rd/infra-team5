package com.team5.surbee.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 16)
    private String username;

    @Setter
    @Column(nullable = true, length = 255)
    private String email;

    @Column(nullable = false, length = 10)
    private String provider;

    @Column(name = "provider_id", nullable = false, length = 64)
    private String providerId; // 소셜 사용자 고유 식별자

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Survey> surveys;

    private User(String username, String email, String provider, String providerId) {
        this.username = username;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
    }

    public static User of(String username, String email, String provider, String providerId) {
        return new User(username, email, provider, providerId);
    }

    public static User of(String username, String provider, String providerId) {
        return new User(username, null, provider, providerId);
    }
}
