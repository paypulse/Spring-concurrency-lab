package com.example.springconcurrencylab.Api.LabAsync.Repository;

import com.example.springconcurrencylab.Entity.QStock;
import com.example.springconcurrencylab.Entity.Stock;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StockRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;


    public Stock findById(Long id) {
        return queryFactory
                .selectFrom(QStock.stock)
                .where(QStock.stock.id.eq(id))
                .fetchOne();
    }

    



}
