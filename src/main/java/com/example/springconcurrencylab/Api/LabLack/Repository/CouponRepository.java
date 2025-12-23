package com.example.springconcurrencylab.Api.LabLack.Repository;


import com.example.springconcurrencylab.Api.LabLack.Dto.CouponRequestDto;
import com.example.springconcurrencylab.Api.LabLack.Dto.CouponResponseDto;
import com.example.springconcurrencylab.Entity.Coupon;
import com.example.springconcurrencylab.Entity.QCoupon;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponRepository {
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;
    private final QCoupon qCoupon = QCoupon.coupon;

    //<editor-fold desc="[조회] select">
    public CouponResponseDto getCouponById(Long id)
    {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                CouponResponseDto.class,
                                qCoupon.id,
                                qCoupon.couponCode,
                                qCoupon.issueCount,
                                qCoupon.limitCount,
                                qCoupon.version
                        )
                )
                .from(qCoupon)
                .where(qCoupon.id.eq(id))
                .fetchOne();
    }
    //</editor-fold desc="[조회] select">

    //<editor-fold desc="[등록] insert">
    public Long saveCoupon(CouponRequestDto req){
        Coupon entity = Coupon.builder()
                .couponCode(req.getCouponCode())
                .issueCount(req.getIssueCount())
                .limitCount(req.getLimitCount())
                .version(req.getVersion())
                .build();
        em.persist(entity);
        return entity.getId();
    }
    //</editor-fold desc="[등록] insert">

    //<editor-fold desc="[수정] update">
    public Boolean updateCoupon(CouponRequestDto req){
        long updated = jpaQueryFactory
                .update(qCoupon)
                .set(qCoupon.couponCode , req.getCouponCode())
                .set(qCoupon.issueCount, req.getIssueCount())
                .set(qCoupon.limitCount , req.getLimitCount())
                .where(
                    qCoupon.id.eq(req.getId())
                ).execute();
        return updated > 0 ; // 성공 여부 반환
    }
    //</editor-fold desc="[수정] update">

    //<editor-fold desc="[삭제] delete">
//    public Boolean deleteCoupon(Long id) {
//        log
//    }


    //</editor-fold desc="[삭제] delete">

}
