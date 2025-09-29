package com.example.springconcurrencylab.Entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


/***
 * contents  : 쿠폰 발급 예시 테이블
 * ****/
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "coupon")
@EntityListeners(AuditingEntityListener.class)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String couponCode;

    @Column(nullable = false)
    private Integer issueCount;

    @Column(nullable = false)
    private Integer limitCount;

    @Version
    private Long version;
}
