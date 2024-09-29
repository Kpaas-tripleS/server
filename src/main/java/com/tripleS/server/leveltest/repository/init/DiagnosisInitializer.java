package com.tripleS.server.leveltest.repository.init;

import com.tripleS.server.global.util.DummyDataInit;
import com.tripleS.server.leveltest.domain.Diagnosis;
import com.tripleS.server.leveltest.repository.DiagnosisRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Order(2)
@RequiredArgsConstructor
@Transactional
@DummyDataInit
public class DiagnosisInitializer implements ApplicationRunner {

    private final DiagnosisRepository diagnosisRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (diagnosisRepository.count() != 0) {
            log.info("Diagnosis data already exists.");
        } else {
            List<Diagnosis> diagnosisList = new ArrayList<>();

            Diagnosis lowLevelDiagnosis = Diagnosis.builder()
                    .level(Diagnosis.DiagnosisLevel.LOW)
                    .feedback("당신의 문해력은 아직 개선의 여지가 많습니다. 일상적인 대화나 간단한 글을 이해하는 데 어려움을 겪을 수 있습니다. 매일매일 익담과 함께하며  꾸준한 노력으로 반드시 향상될 수 있으니 힘내세요!")
                    .build();

            Diagnosis mediumLevelDiagnosis = Diagnosis.builder()
                    .level(Diagnosis.DiagnosisLevel.MEDIUM)
                    .feedback("당신의 문해력은 평균 수준입니다. 일상적인 의사소통에는 큰 문제가 없지만, 복잡한 텍스트나 전문적인 내용을 이해하는 데 어려움을 겪을 수 있습니다. 익담과 함께라면 더 노력하면 높은 수준의 문해력을 갖출 수 있어요!")
                    .build();

            Diagnosis highLevelDiagnosis = Diagnosis.builder()
                    .level(Diagnosis.DiagnosisLevel.HIGH)
                    .feedback("축하합니다! 당신의 문해력은 상위 수준입니다. 복잡한 텍스트와 추상적인 개념을 이해하는 데 어려움이 없으며, 자신의 생각을 명확하게 표현할 수 있습니다. 이제는 비판적 읽기 능력을 더욱 발전시켜 보세요. 익담과 함께라면 더욱 향상된 실력을 자딜 수 있어요!")
                    .build();

            diagnosisList.add(lowLevelDiagnosis);
            diagnosisList.add(mediumLevelDiagnosis);
            diagnosisList.add(highLevelDiagnosis);

            diagnosisRepository.saveAll(diagnosisList);
            log.info("Diagnosis data initialized successfully.");
        }
    }
}