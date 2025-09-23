package com.example.springconcurrencylab.Entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "stock")
@EntityListeners(AuditingEntityListener.class)
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Quantity quantity;

    //낙관적 lock 버전 컬럼
    @Version
    private Long version;

}

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
class Quantity {
    private int value;

    public Quantity decrease(int amount) {
        if (value - amount < 0) {
            throw new IllegalArgumentException("재고 부족");
        }
        return new Quantity(value - amount); // ✅ 불변성 유지
    }
}

