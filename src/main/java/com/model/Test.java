package com.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "level_id")
    @NotNull
    private Level level;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "quiz_test",joinColumns = {@JoinColumn(name = "test_id")},
            inverseJoinColumns = {@JoinColumn(name = "quiz_id")})
    @NotNull
    private Set<Quiz> quizzes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "test_category",joinColumns = {@JoinColumn(name = "test_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    @NotNull
    private Set<Category> categories;

    @NotNull
    private int passScore;
    @NotNull
    private Time maxTime;
    @NotNull
    private String name;
}
