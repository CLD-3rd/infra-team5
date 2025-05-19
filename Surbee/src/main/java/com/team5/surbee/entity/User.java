package com.team5.surbee.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 16, nullable = false)
    private String username;

    @Column(length = 255, nullable = false, unique = true)
    private String email;

//    @Column(length = 100)
//    private String password; // 소셜 로그인 사용자도 대비해서 nullable

    @Column(length = 20, nullable = false)
    private String provider; // ex. google, kakao

    @Column(length = 255, nullable = false)
    private String providerId; // 소셜 사용자 고유 식별자

    @Column(nullable = false)
    private String createdAt;

    // 예: @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Survey> surveys;
}
