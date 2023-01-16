package com.repository.result;

import com.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITestResultRepository extends JpaRepository<TestResult, Long> {
}
