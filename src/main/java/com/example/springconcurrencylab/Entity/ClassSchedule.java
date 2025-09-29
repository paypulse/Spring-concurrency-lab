package com.example.springconcurrencylab.Entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;



/***
 * contents  : 강의 상태 예시 테이블
 * ****/
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "classSchedule")
@EntityListeners(AuditingEntityListener.class)
public class ClassSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String className;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassStatus classStatus;

    @Version
    private Long version;
}

enum ClassStatus {
    PLANNED, ONGOING, ENDED
}
