package com.tripleS.server.leveltest.repository;

import com.tripleS.server.leveltest.domain.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
    Diagnosis findByLevel(Diagnosis.DiagnosisLevel level);
}