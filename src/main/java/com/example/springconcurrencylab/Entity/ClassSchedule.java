package com.example.springconcurrencylab.Entity;


import com.example.springconcurrencylab.Define.EntityEnum;
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
    private EntityEnum.ClassStatus classStatus;

    //<editor-fold desc="Optimistic Lock 버전 관리">
    @Version
    private Long version;
    //</editor-fold desc="Optimistic Lock 버전 관리">
}

