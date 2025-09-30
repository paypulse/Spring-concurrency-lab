package com.example.springconcurrencylab.Entity;

import com.example.springconcurrencylab.Define.EntityEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/***
 * contents  : 주문 / 결제
 * ****/
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "orderPayment")
@EntityListeners(AuditingEntityListener.class)
public class OrderPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderNo;

    @Column (nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityEnum.OrderStatus orderStatus;

    @Version
    private Long version;
}
