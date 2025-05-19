package com.team5.surbee.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "survey")
public class Survey {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 45)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private boolean isPublic;

    @Column(nullable = false)
    private boolean isClosed;

    @Column(length = 45)
    private String password;

    @Column(nullable = false)
    private Integer submissionCount = 0;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Submission> submissions = new ArrayList<>();

    private Survey(String title, String description, boolean isPublic, boolean isClosed, String password, Integer submissionCount, User user, List<Question> questions, List<Submission> submissions) {
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.isClosed = isClosed;
        this.password = password;
        this.submissionCount = submissionCount;
        this.user = user;
        this.questions = questions;
        this.submissions = submissions;
    }

    public static Survey of(String title, String description, boolean isPublic, boolean isClosed, String password, Integer submissionCount, User user, List<Question> questions, List<Submission> submissions) {
        return new Survey(title, description, isPublic, isClosed, password, submissionCount, user, questions, submissions);
    }


}