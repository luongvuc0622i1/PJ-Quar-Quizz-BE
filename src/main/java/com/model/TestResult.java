package com.model;

import com.model.jwt.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "examTest")
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numOfTA;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "examQuiz_examTest",joinColumns = {@JoinColumn(name = "examTest_id")},
            inverseJoinColumns = {@JoinColumn(name = "examQuiz_id")})
    @NotNull
    private Set<QuizResult> examQuizzes;

    @ManyToOne
    @JoinColumn(name = "appUser_id")
    @NotNull
    private AppUser appUser;
}
