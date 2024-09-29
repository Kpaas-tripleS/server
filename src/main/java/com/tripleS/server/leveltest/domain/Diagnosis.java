package com.tripleS.server.leveltest.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "diagnoses")
@Builder
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor // 모든 필드를 포함하는 생성자 추가
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiagnosisLevel level;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    public enum DiagnosisLevel {
        LOW, MEDIUM, HIGH
    }

    // Getter와 Setter 메서드는 그대로 유지
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiagnosisLevel getLevel() {
        return level;
    }

    public void setLevel(DiagnosisLevel level) {
        this.level = level;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}