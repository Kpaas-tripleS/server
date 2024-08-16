package com.tripleS.server.summary.repository;

import com.tripleS.server.summary.domain.MySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MySummaryRepository extends JpaRepository<MySummary, Long> {
}
