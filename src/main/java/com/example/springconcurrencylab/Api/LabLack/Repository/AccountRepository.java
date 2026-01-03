package com.example.springconcurrencylab.Api.LabLack.Repository;


import com.example.springconcurrencylab.Api.LabLack.Dto.AccountsResponseDto;
import com.example.springconcurrencylab.Entity.QAccounts;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    private final QAccounts qAccounts = QAccounts.accounts;

    //<editor-fold desc="[조회] select">
    public AccountsResponseDto getAccounts(Long id)
    {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                            AccountsResponseDto.class,
                            qAccounts.id,
                            qAccounts.ownerName,
                            qAccounts.balance
                        )
                )
                .from(qAccounts)
                .where(qAccounts.id.eq(id))
                .fetchOne();
    }
    //</editor-fold desc="[조회] select">

    //<editor-fold desc="[등록] insert">

    //</editor-fold desc="[등록] insert">

    //<editor-fold desc="[수정] update">
    //</editor-fold desc="[수정] update">

    //<editor-fold desc="[삭제] delete">
    //</editor-fold desc="[삭제] delete">

}
