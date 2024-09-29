package com.tripleS.server.leveltest.repository;

import com.tripleS.server.leveltest.domain.LevelTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelTestRepository extends JpaRepository<LevelTest, Long> {
    List<LevelTest> findTop10ByOrderByIdAsc();
}