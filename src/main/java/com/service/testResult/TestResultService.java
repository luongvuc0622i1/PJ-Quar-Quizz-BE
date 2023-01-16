package com.service.testResult;

import com.model.TestResult;
import com.repository.result.ITestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TestResultService implements ITestResultService {
    @Autowired
    private ITestResultRepository testResultRepository;

    @Override
    public Iterable<TestResult> findAll() {
        return testResultRepository.findAll();
    }

    @Override
    public Optional<TestResult> findById(Long id) {
        return testResultRepository.findById(id);
    }

    @Override
    public TestResult save(TestResult model) {
        return testResultRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        testResultRepository.deleteById(id);
    }
}
