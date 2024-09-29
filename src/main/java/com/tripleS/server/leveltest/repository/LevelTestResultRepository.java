package com.tripleS.server.leveltest.repository;

import com.tripleS.server.leveltest.domain.LevelTestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelTestResultRepository extends JpaRepository<LevelTestResult, Long> {
    boolean existsByUserId(Long userId);
}
