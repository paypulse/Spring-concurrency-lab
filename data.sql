-- class schedule
insert into class_schedule (id, class_name, class_status, version)
values  (null,'Spring Boot 입문 강의', 'PLANNED', 0 ),
        (null,'Java 동시성 제어 실습', 'ONGOING', 1),
        (null,'Kotlin + QueryDSL 고급편', 'ENDED', 2),
        (null,'AI와 데이터베이스 통합', 'PLANNED', 0),
        (null,'실전 프로젝트 관리 워크샵', 'ONGOING', 3);

/**
  비관적 락 확인 sql 쿼리
  */

-- 현재 transaction 보기
select trx_id,
       trx_mysql_thread_id,
       trx_state,
       trx_started,
       trx_query,
       trx_wait_started
from information_schema.innodb_trx;

-- 락 대기 관계 보기
SELECT
    r.trx_id AS WAITING_TRX_ID,
    r.trx_started AS WAITING_START_TIME,
    r.trx_state AS WAITING_STATE,
    b.trx_id AS  BLOCKING_TRX_ID,
    b.trx_mysql_thread_id AS BLOCKING_THREAD,
    b.trx_query AS BLOCKING_QUERY
FROM performance_schema.data_lock_waits AS w
         JOIN information_schema.INNODB_TRX AS b  ON b.trx_id = w.BLOCKING_ENGINE_TRANSACTION_ID
         JOIN information_schema.INNODB_TRX AS r  ON r.trx_id = w.REQUESTING_ENGINE_TRANSACTION_ID;
ORDER BY r.trx_started;

