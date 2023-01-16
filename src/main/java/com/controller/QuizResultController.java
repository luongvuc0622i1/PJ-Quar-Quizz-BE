package com.controller;

import com.model.QuizResult;
import com.service.quizResult.IQuizResultService;
import com.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/user/quizResult")
public class QuizResultController {
    @Autowired
    private IQuizResultService quizResultService;

    @Autowired
    private IUserService userService;

    @GetMapping("")
    public ResponseEntity<Iterable<QuizResult>> getAllQuizResult(){
        Iterable<QuizResult> examQuizList = quizResultService.findAll();
        return new ResponseEntity<>(examQuizList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResult> findExamQuizById(@PathVariable Long id) {
        Optional<QuizResult> examQuizOptional = quizResultService.findById(id);
        return examQuizOptional.map(ExamQuiz -> new ResponseEntity<>(ExamQuiz, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("")
    public ResponseEntity<QuizResult> createExamQuiz(@RequestBody QuizResult form) {
        form.setQuiz(form.getQuiz()); //not done
        form.setTest(form.getTest()); //not done
        form.setAppUser(userService.findById(form.getAppUser().getId()).get());
        QuizResult quizResult = quizResultService.save(form);
        return new ResponseEntity<>(quizResult, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<QuizResult> deleteExamQuiz(@PathVariable Long id) {
        Optional<QuizResult> examQuizOptional = quizResultService.findById(id);
        if (!examQuizOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        quizResultService.remove(id);
        return new ResponseEntity<>(examQuizOptional.get(), HttpStatus.NO_CONTENT);
    }
}
