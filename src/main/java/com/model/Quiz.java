package com.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String answer;

    @NotNull
    private String correct_answer;

    @ManyToOne
    @JoinColumn(name = "typeQuiz_id")
    @NotNull
    private TypeQuiz typeQuizzes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "quiz_category",joinColumns = {@JoinColumn(name = "quiz_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    @NotNull
    private Set<Category> categories;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "level_id")
    @NotNull
    private Level level;
}
