package com.team5.surbee.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 45, nullable = false)
    private String tiitle;

    @Column(length = 45)
    private String surveycol; // 사용 용도에 따라 수정 가능

    @Column(nullable = false)
    private boolean isPublic;

    @Column(nullable = false)
    private boolean isClosed;

    @Column(length = 45, nullable = false)
    private String createdAt;

    @Column(length = 255)
    private String description;

    @Column(length = 45)
    private String password;

    @Column(nullable = false)
    private int submissionCount;

    // 연관 관계 설정 예시
    // @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Question> questions;

    // @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Submission> submissions;
}