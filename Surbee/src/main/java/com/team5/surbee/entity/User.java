package com.team5.surbee.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@ToString
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="user")
public class User {
    @Id
    @Column(name="id")
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
    private String providerId;

    @CreatedDate
    private Timestamp createdAt;
    private User (String username,String email,String provider, String providerId){
        this.username=username;
        this.email=email;
        this.provider=provider;
        this.providerId=providerId;
    }
    public static User of(String username, String email, String provider, String providerId){
        return new User(username, email, provider, providerId);
    }
    public static User of(String username, String provider, String providerId){
        return new User(username, null, provider, providerId);
    }
}
