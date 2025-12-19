package com.example.springconcurrencylab.Entity;

import com.example.springconcurrencylab.Define.EntityEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/***
 * 트랜잭션 격리성 예제 테이블
 * ****/
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "accounts")
@EntityListeners(AuditingEntityListener.class)
public class Accounts {

    @Id
    private Long id;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private int balance;

}
