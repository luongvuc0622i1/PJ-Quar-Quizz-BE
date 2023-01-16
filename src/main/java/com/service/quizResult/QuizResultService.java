package com.service.quizResult;

import com.model.QuizResult;
import com.repository.result.IQuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuizResultService implements IQuizResultService {
    @Autowired
    private IQuizResultRepository quizResultRepository;

    @Override
    public Iterable findAll() {
        return quizResultRepository.findAll();
    }

    @Override
    public Optional findById(Long id) {
        return quizResultRepository.findById(id);
    }

    @Override
    public QuizResult save(QuizResult model) {
        return quizResultRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        quizResultRepository.deleteById(id);
    }
}
