package com.example.springconcurrencylab.api;


import com.example.springconcurrencylab.Api.LabLack.Repository.ClassScheduleRepository;
import com.example.springconcurrencylab.Define.EntityEnum;
import com.example.springconcurrencylab.Entity.ClassSchedule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class OptimisticLockConcurrencyTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ClassScheduleRepository classScheduleRepository;

    private Long testClassId;

    @BeforeEach
    void setUp() {
        // 테스트용 수업 생성
        ClassSchedule schedule = ClassSchedule
                .builder()
                .className("동시 호출 테스트")
                .classStatus(EntityEnum.ClassStatus.ONGOING)
                .build();


    }




}
