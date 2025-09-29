package com.example.springconcurrencylab.Entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/***
 * contents  : 좌석 예약 예시 테이블
 * ****/
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "SeatReservation")
@EntityListeners(AuditingEntityListener.class)
public class SeatReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seatNo")
    private String seatNo;

    @Column(name = "reserved")
    private Boolean reserved;

    @Version
    private Long version;

}
