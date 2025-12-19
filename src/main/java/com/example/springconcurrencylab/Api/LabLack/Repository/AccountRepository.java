package com.example.springconcurrencylab.Api.LabLack.Repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    private final AccountRepository accountRepository;


    //<editor-fold desc="[조회] select">

    //</editor-fold desc="[조회] select">


    //<editor-fold desc="[등록] insert">

    //</editor-fold desc="[등록] insert">


    //<editor-fold desc="[수정] update">

    //</editor-fold desc="[수정] update">


    //<editor-fold desc="[삭제] delete">

    //</editor-fold desc="[삭제] delete">

}
