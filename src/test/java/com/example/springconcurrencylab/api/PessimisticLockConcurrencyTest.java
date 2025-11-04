package com.example.springconcurrencylab.api;


import com.example.springconcurrencylab.Api.LabLack.Dto.ClassScheduleResponseDto;
import com.example.springconcurrencylab.Api.LabLack.Repository.ClassScheduleRepository;
import com.example.springconcurrencylab.Api.LabLack.Service.LabLackService;
import com.example.springconcurrencylab.Define.EntityEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class PessimisticLockConcurrencyTest {

    @Autowired
    private LabLackService labLackService;

    @Autowired
    private ClassScheduleRepository classScheduleRepository;

    @Test
    void TestPessimisticLock_concurrentUpdate() throws Exception{

        // 동시 접속 수
        int threadCount = 5;

        // 고정 쓰레드 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        log.info("Thread pool 생성 완료 (poolSize={})", threadCount);
        // 동기화 도구
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);


        int count = 0;
        do {
            executorService.submit(()->{
                String threadName = "Worker";
                log.info("{}. 대기 중 (풀에서 쓰레드 할당 대기)", Thread.currentThread().getName());
                try {
                    log.info("쓰레드 실행 시작 ");
                    labLackService.getIdPessimistic(18L);
                    log.info("트랜잭션 완료");
                } catch (Exception e) {
                    log.error("PessimisticLockException in Thread : {} -> {} ",Thread.currentThread().getName(), e.getMessage());
                } finally {
                    countDownLatch.countDown();
                    log.info("쓰레드 종료 ");
                }
            });
            count ++;
        }while(count < threadCount);
        //모든 쓰레드 종료 대기
        countDownLatch.await(30, TimeUnit.SECONDS);
        //최종 확인 상태
        ClassScheduleResponseDto result =
                classScheduleRepository.findById(18L);
        //검증
        assertThat(result.getClassStatus()).isEqualTo(EntityEnum.ClassStatus.ENDED.toString());

    }


}
