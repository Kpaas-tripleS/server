package com.tripleS.server.leveltest.repository.init;

import com.tripleS.server.global.util.DummyDataInit;
import com.tripleS.server.leveltest.domain.LevelTest;
import com.tripleS.server.leveltest.repository.LevelTestRepository;
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
public class LevelTestInitializer implements ApplicationRunner {

    private final LevelTestRepository levelTestRepository;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Deleting existing level test data...");
        levelTestRepository.deleteAll();  // Delete existing data

        log.info("Initializing level test data...");

        List<LevelTest> levelTestList = new ArrayList<>();

        // Create dummy level test data
        LevelTest LEVEL_TEST1 = new LevelTest();
        LEVEL_TEST1.setQuestion("다음 문장에서 '철수는 사과를 먹었다'에서 '사과'의 의미는 무엇인가요?");
        LEVEL_TEST1.setOptionOne("1. 잘못을 빌다");
        LEVEL_TEST1.setOptionTwo("2. 과일");
        LEVEL_TEST1.setOptionThree("3. 대화를 하다");
        LEVEL_TEST1.setOptionFour("4. 규칙을 따르다");
        LEVEL_TEST1.setCorrectAnswer("2");

        LevelTest LEVEL_TEST2 = new LevelTest();
        LEVEL_TEST2.setQuestion("다음 문장에서 '김씨는 계획을 세웠다'에서 '계획'의 뜻으로 알맞은 것을 고르세요.");
        LEVEL_TEST2.setOptionOne("1. 일정");
        LEVEL_TEST2.setOptionTwo("2. 계산");
        LEVEL_TEST2.setOptionThree("3. 준비");
        LEVEL_TEST2.setOptionFour("4. 선택");
        LEVEL_TEST2.setCorrectAnswer("1");

        LevelTest LEVEL_TEST3 = new LevelTest();
        LEVEL_TEST3.setQuestion("다음 문장에서 '철수는 소를 키운다'에서 '소'의 뜻은 무엇인가요?");
        LEVEL_TEST3.setOptionOne("1. 소리");
        LEVEL_TEST3.setOptionTwo("2. 소(동물)");
        LEVEL_TEST3.setOptionThree("3. 소주");
        LEVEL_TEST3.setOptionFour("4. 소문");
        LEVEL_TEST3.setCorrectAnswer("2");

        LevelTest LEVEL_TEST4 = new LevelTest();
        LEVEL_TEST4.setQuestion("다음 문장에서 '사람은 서로 존중해야 한다'에서 '존중'의 의미로 알맞은 것을 고르세요.");
        LEVEL_TEST4.setOptionOne("1. 존경하다");
        LEVEL_TEST4.setOptionTwo("2. 귀중하게 여기다");
        LEVEL_TEST4.setOptionThree("3. 감사를 표하다");
        LEVEL_TEST4.setOptionFour("4. 사랑하다");
        LEVEL_TEST4.setCorrectAnswer("2");

        LevelTest LEVEL_TEST5 = new LevelTest();
        LEVEL_TEST5.setQuestion("다음 문장에서 '문제는 생각보다 복잡했다'에서 '복잡하다'의 의미로 알맞은 것을 고르세요.");
        LEVEL_TEST5.setOptionOne("1. 어렵다");
        LEVEL_TEST5.setOptionTwo("2. 단순하다");
        LEVEL_TEST5.setOptionThree("3. 재미있다");
        LEVEL_TEST5.setOptionFour("4. 빠르다");
        LEVEL_TEST5.setCorrectAnswer("1");

        LevelTest LEVEL_TEST6 = new LevelTest();
        LEVEL_TEST6.setQuestion("다음 문장에서 '그녀는 눈이 높다'의 의미로 알맞은 것을 고르세요.");
        LEVEL_TEST6.setOptionOne("1. 키가 크다");
        LEVEL_TEST6.setOptionTwo("2. 기대가 크다");
        LEVEL_TEST6.setOptionThree("3. 비판적이다");
        LEVEL_TEST6.setOptionFour("4. 신중하다");
        LEVEL_TEST6.setCorrectAnswer("2");

        LevelTest LEVEL_TEST7 = new LevelTest();
        LEVEL_TEST7.setQuestion("다음 문장에서 '철수는 달리기를 좋아한다'에서 '달리기'의 뜻은 무엇인가요?");
        LEVEL_TEST7.setOptionOne("1. 뛰는 것");
        LEVEL_TEST7.setOptionTwo("2. 도망치는 것");
        LEVEL_TEST7.setOptionThree("3. 일하는 것");
        LEVEL_TEST7.setOptionFour("4. 빠르게 걷는 것");
        LEVEL_TEST7.setCorrectAnswer("1");

        LevelTest LEVEL_TEST8 = new LevelTest();
        LEVEL_TEST8.setQuestion("다음 문장에서 '그녀는 거침없이 말했다'에서 '거침없다'의 뜻은 무엇인가요?");
        LEVEL_TEST8.setOptionOne("1. 신중하다");
        LEVEL_TEST8.setOptionTwo("2. 망설이지 않는다");
        LEVEL_TEST8.setOptionThree("3. 서두르다");
        LEVEL_TEST8.setOptionFour("4. 침착하다");
        LEVEL_TEST8.setCorrectAnswer("2");

        LevelTest LEVEL_TEST9 = new LevelTest();
        LEVEL_TEST9.setQuestion("다음 문장에서 '이 문제는 간단하다'의 의미로 가장 적절한 것을 고르세요.");
        LEVEL_TEST9.setOptionOne("1. 단순하다");
        LEVEL_TEST9.setOptionTwo("2. 어렵다");
        LEVEL_TEST9.setOptionThree("3. 애매하다");
        LEVEL_TEST9.setOptionFour("4. 해결하기 어렵다");
        LEVEL_TEST9.setCorrectAnswer("1");

        LevelTest LEVEL_TEST10 = new LevelTest();
        LEVEL_TEST10.setQuestion("다음 문장에서 '철수는 은행에서 돈을 찾았다'에서 '은행'의 뜻으로 알맞은 것을 고르세요.");
        LEVEL_TEST10.setOptionOne("1. 강변");
        LEVEL_TEST10.setOptionTwo("2. 나무의 일종");
        LEVEL_TEST10.setOptionThree("3. 금융 기관");
        LEVEL_TEST10.setOptionFour("4. 돈을 주는 곳");
        LEVEL_TEST10.setCorrectAnswer("3");



        levelTestList.add(LEVEL_TEST1);
        levelTestList.add(LEVEL_TEST2);
        levelTestList.add(LEVEL_TEST3);
        levelTestList.add(LEVEL_TEST4);
        levelTestList.add(LEVEL_TEST5);
        levelTestList.add(LEVEL_TEST6);
        levelTestList.add(LEVEL_TEST7);
        levelTestList.add(LEVEL_TEST8);
        levelTestList.add(LEVEL_TEST9);
        levelTestList.add(LEVEL_TEST10);

        // Save each level test item and handle exceptions
        for (LevelTest levelTest : levelTestList) {
            try {
                levelTestRepository.save(levelTest);
                log.info("Saved level test: {}", levelTest.getQuestion());
            } catch (Exception e) {
                log.error("Error saving level test: {} - {}", levelTest.getQuestion(), e.getMessage());
            }
        }
    }
}