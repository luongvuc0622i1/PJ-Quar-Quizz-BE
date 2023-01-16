package com.repository.result;

import com.model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizResultRepository extends JpaRepository<QuizResult, Long> {
}
