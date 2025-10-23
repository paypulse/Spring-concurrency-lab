package com.example.springconcurrencylab.api;


import com.example.springconcurrencylab.Api.LabLack.Dto.ClassScheduleRequestDto;
import com.example.springconcurrencylab.Api.LabLack.Dto.ClassScheduleResponseDto;
import com.example.springconcurrencylab.Api.LabLack.Repository.ClassScheduleRepository;
import com.example.springconcurrencylab.Api.LabLack.Service.LabLackService;
import com.example.springconcurrencylab.Define.EntityEnum;
import com.example.springconcurrencylab.Entity.ClassSchedule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class OptimisticLockConcurrencyTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ClassScheduleRepository classScheduleRepository;

    private Long testClassId;
    @Autowired
    private LabLackService labLackService;

    @BeforeEach
    void setUp() {
        // 테스트용 수업 생성
        ClassScheduleRequestDto schedule = ClassScheduleRequestDto
                .builder()
                .className("동시 호출 테스트")
                .classStatus(EntityEnum.ClassStatus.ONGOING)
                .build();

        testClassId = classScheduleRepository.saveClassSchedule(schedule);
        log.info("테스트 수업 생성 :  id = {}" , testClassId);

    }

    @Test
    void testOptimisticLock_concurrentUpdate() throws Exception {
        // 동시 접속 수
        int threadCount  = 5;
        // 쓰레드 공장 , 고정 쓰레드 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        //문지기, 동기화 도구
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        int count  = 0;

        do {
            log.info("[start] Thread count : {}", threadCount);
            //쓰레드 비동기 처리 메서드
            executorService.submit(()->{
                try {
                    labLackService.getIdOptimistic(testClassId);
                    log.info("성공 Thread : {} ", Thread.currentThread().getName());
                } catch (Exception e) {
                    log.error("OptimisticLockException in Thread : {} -> {}", Thread.currentThread().getName(), e.getMessage());

                }finally {
                    countDownLatch.countDown();
                }
            });
            count++;
            log.info("[end] Thread");
        }while(count < threadCount);

        // 모든 쓰레드 종료 대기
        countDownLatch.await();

        //최종 확인 상태
        ClassScheduleResponseDto result =
                classScheduleRepository.findById(testClassId);
        if (result == null) {
            log.warn("테스트 강의 없음. {}", testClassId);
        }
        log.info("최종 상태 : {}, version= {}", result.getClassStatus(), result.getVersion());

        //검증
        assertThat(result.getClassStatus()).isEqualTo(EntityEnum.ClassStatus.ENDED.toString());
        assertThat(result.getVersion()).isGreaterThan(0);


    }




}
