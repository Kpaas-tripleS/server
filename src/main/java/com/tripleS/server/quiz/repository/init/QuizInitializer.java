package com.tripleS.server.quiz.repository.init;

import com.tripleS.server.global.util.DummyDataInit;
import com.tripleS.server.quiz.domain.Quiz;
import com.tripleS.server.quiz.domain.Quiz.DifficultyLevel;
import com.tripleS.server.quiz.repository.QuizRepository;
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
public class QuizInitializer implements ApplicationRunner {

    private final QuizRepository quizRepository;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Deleting existing quiz data...");
        quizRepository.deleteAll();  // 기존 데이터를 삭제

        log.info("Initializing quiz data...");

        List<Quiz> quizList = new ArrayList<>();

        // 퀴즈 데이터 생성
        Quiz DUMMY_QUIZ1 = Quiz.builder()
                .question("다음 문장에서 밑줄 친 단어의 뜻으로 알맞은 것을 고르시오(하) '중식'")
                .choiceOne("1. 중국 음식")
                .choiceTwo("2. 간식")
                .choiceThree("3. 점심 식사")
                .choiceFour("4. 저녁 식사")
                .answer("3")
                .level(DifficultyLevel.BEGINNER)
                .build();

        Quiz DUMMY_QUIZ2 = Quiz.builder()
                .question("다음 문장에서 밑줄 친 단어의 뜻으로 알맞은 것을 고르시오(하) '금일'")
                .choiceOne("1. 금요일")
                .choiceTwo("2. 오늘")
                .choiceThree("3. 내일")
                .choiceFour("4. 이번 주")
                .answer("2")
                .level(DifficultyLevel.BEGINNER)
                .build();

        Quiz DUMMY_QUIZ3 = Quiz.builder()
                .question("다음 문장에서 빈칸에 들어갈 가장 적절한 단어를 고르시오(중) '--- 끝에 마침내 목표를 이뤘다'")
                .choiceOne("1. 노력")
                .choiceTwo("2. 좌절")
                .choiceThree("3. 도전")
                .choiceFour("4. 절망")
                .answer("1")
                .level(DifficultyLevel.INTERMEDIATE)
                .build();

        Quiz DUMMY_QUIZ4 = Quiz.builder()
                .question("과제 제출 기한에 맞는 올바른 표현을 고르세요.")
                .choiceOne("1. 금일 제출하세요.")
                .choiceTwo("2. 하루 뒤 제출하세요.")
                .choiceThree("3. 사흘 뒤 제출하세요.")
                .choiceFour("4. 이틀 뒤 제출하세요.")
                .answer("3")
                .level(DifficultyLevel.BEGINNER)
                .build();

        Quiz DUMMY_QUIZ5 = Quiz.builder()
                .question("이팔청춘의 나이에 해당하는 나이를 고르세요.")
                .choiceOne("1. 28세")
                .choiceTwo("2. 16세")
                .choiceThree("3. 82세")
                .choiceFour("4. 50세")
                .answer("2")
                .level(DifficultyLevel.BEGINNER)
                .build();

        Quiz DUMMY_QUIZ6 = Quiz.builder()
                .question("다음 중 '책임을 지다'와 가장 비슷한 뜻을 가진 단어를 고르세요.")
                .choiceOne("1. 무책임하다")
                .choiceTwo("2. 책임을 회피하다")
                .choiceThree("3. 책임을 떠맡다")
                .choiceFour("4. 책임지지 않다")
                .answer("3")
                .level(DifficultyLevel.INTERMEDIATE)
                .build();

        Quiz DUMMY_QUIZ7 = Quiz.builder()
                .question("다음 문장에서 빈칸에 들어갈 적절한 단어를 고르세요. '나는 시험에 대비하기 위해 매일 ______ 했다.'")
                .choiceOne("1. 운동")
                .choiceTwo("2. 공부")
                .choiceThree("3. 휴식")
                .choiceFour("4. 여행")
                .answer("2")
                .level(DifficultyLevel.BEGINNER)
                .build();

        Quiz DUMMY_QUIZ8 = Quiz.builder()
                .question("다음 중 '경험'의 의미로 가장 적절한 단어는 무엇인가요?")
                .choiceOne("1. 사고")
                .choiceTwo("2. 경험")
                .choiceThree("3. 습관")
                .choiceFour("4. 관습")
                .answer("2")
                .level(DifficultyLevel.BEGINNER)
                .build();

        Quiz DUMMY_QUIZ9 = Quiz.builder()
                .question("다음 문장에서 밑줄 친 단어의 의미로 가장 적절한 것을 고르세요. '그는 어려운 상황에도 불구하고 ______ 의지를 보여주었다.'")
                .choiceOne("1. 포기")
                .choiceTwo("2. 인내")
                .choiceThree("3. 절망")
                .choiceFour("4. 방해")
                .answer("2")
                .level(DifficultyLevel.INTERMEDIATE)
                .build();

        Quiz DUMMY_QUIZ10 = Quiz.builder()
                .question("다음 중 올바른 문장을 고르세요.")
                .choiceOne("1. 나는 어제 친구와 함께 갔다왔습니다.")
                .choiceTwo("2. 나는 어제 친구랑 같이 갔다왔다.")
                .choiceThree("3. 나는 어제 친구하고 같이 갔다왔습니다.")
                .choiceFour("4. 나는 어제 친구와 함께 갔다왔다.")
                .answer("4")
                .level(DifficultyLevel.ADVANCED)
                .build();

        Quiz DUMMY_QUIZ11 = Quiz.builder()
                .question("다음 중 '낭중지추(囊中之錐)'의 의미와 가장 가까운 속담은 무엇인가요?")
                .choiceOne("1. 호랑이도 제 말 하면 온다.")
                .choiceTwo("2. 세 살 버릇 여든까지 간다.")
                .choiceThree("3. 뛰는 놈 위에 나는 놈 있다.")
                .choiceFour("4. 빛이 나는 사람은 숨길 수 없다.")
                .answer("4")
                .level(DifficultyLevel.ADVANCED)
                .build();

        Quiz DUMMY_QUIZ12 = Quiz.builder()
                .question("다음 중 맞춤법이 올바르게 쓰인 문장은 무엇인가요?")
                .choiceOne("1. 그는 나를 믿어주었기에, 나는 그것을 꼭 기억한다.")
                .choiceTwo("2. 나와 함께 할 사람은 네가 될 거라고 믿었어.")
                .choiceThree("3. 시험 결과가 나왔는데도, 실감이 나지 않아.")
                .choiceFour("4. 어려운 상황 속에서도 그는 굳은 의지를 보였다고 하였다.")
                .answer("1")
                .level(DifficultyLevel.ADVANCED)
                .build();

        Quiz DUMMY_QUIZ13 = Quiz.builder()
                .question("다음 중 사자성어 '고진감래(苦盡甘來)'와 의미가 가장 비슷한 속담은 무엇인가요?")
                .choiceOne("1. 고생 끝에 낙이 온다.")
                .choiceTwo("2. 가는 말이 고와야 오는 말이 곱다.")
                .choiceThree("3. 천 리 길도 한 걸음부터 시작한다.")
                .choiceFour("4. 우물 안 개구리")
                .answer("1")
                .level(DifficultyLevel.ADVANCED)
                .build();

        Quiz DUMMY_QUIZ14 = Quiz.builder()
                .question("다음 문장에서 밑줄 친 단어의 의미로 가장 적절한 것을 고르세요. '그는 친구의 배신으로 인해 크게 낙담하였다.'")
                .choiceOne("1. 기뻐하다")
                .choiceTwo("2. 실망하다")
                .choiceThree("3. 놀라다")
                .choiceFour("4. 분노하다")
                .answer("2")
                .level(DifficultyLevel.ADVANCED)
                .build();

        Quiz DUMMY_QUIZ15 = Quiz.builder()
                .question("다음 중 단어의 쓰임이 옳은 문장은 무엇인가요?")
                .choiceOne("1. 그는 일에 대한 열의가 매우 강한 사람이다.")
                .choiceTwo("2. 나는 시험 결과에 대해 기대치 않았다.")
                .choiceThree("3. 그녀는 사과를 내밀며 미안한 기색을 비추었다.")
                .choiceFour("4. 모두들 오랜만에 만난 친구를 반갑게 맞쳐주었다.")
                .answer("1")
                .level(DifficultyLevel.ADVANCED)
                .build();

        // 퀴즈 리스트에 추가
        quizList.add(DUMMY_QUIZ1);
        quizList.add(DUMMY_QUIZ2);
        quizList.add(DUMMY_QUIZ3);
        quizList.add(DUMMY_QUIZ4);
        quizList.add(DUMMY_QUIZ5);
        quizList.add(DUMMY_QUIZ6);
        quizList.add(DUMMY_QUIZ7);
        quizList.add(DUMMY_QUIZ8);
        quizList.add(DUMMY_QUIZ9);
        quizList.add(DUMMY_QUIZ10);
        quizList.add(DUMMY_QUIZ11);
        quizList.add(DUMMY_QUIZ12);
        quizList.add(DUMMY_QUIZ13);
        quizList.add(DUMMY_QUIZ14);
        quizList.add(DUMMY_QUIZ15);

        // 개별 퀴즈 저장 및 예외 처리
        for (Quiz quiz : quizList) {
            try {
                Quiz savedQuiz = quizRepository.save(quiz);  // 저장 후 ID 반환
                log.info("Saved quiz: {} with ID: {}", savedQuiz.getQuestion(), savedQuiz.getQuizId());
            } catch (Exception e) {
                log.error("Error saving quiz: {} - {}", quiz.getQuestion(), e.getMessage());
            }
        }
    }
}