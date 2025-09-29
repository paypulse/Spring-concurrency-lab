package com.example.springconcurrencylab.Entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


/***
 * contents  : 투표 시스템
 * ****/
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "pollSystem")
@EntityListeners(AuditingEntityListener.class)
public class PollSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String optionText;

    @Column(nullable = false)
    private Integer voteCount = 0;

    @Version
    private Long version;
}
