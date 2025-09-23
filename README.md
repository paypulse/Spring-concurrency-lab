#  Spring  Boot에서 DB 동시성 제어와 비동기 처리 실험을 위한 프로젝트 
```
   안타깝지만 언어는 : JAVA 
   spring boot + MYSQL + JPA + QueryDsl 
```

# 주요 기능
- 동시성 제어 (lab.lack)
  - 낙관적 락 (@Value)
    -  @Value 컬럼을 두고 Flush 시점에 값이 바뀌었는지 검증 ->  OptimisticLockException 발생시 재시도 로직 필요
      ```
       [OptimisticLock] Stock id=1, version=2 → 변경 감지됨 → OptimisticLockException
      ```
    
  - 비관적 락(PERSSIMISTIC_WRITE , SELECT ... FOR UPDATE) 
    -  Querydsl  .setLockMode(LockModeType.PERSSIMISTIC_WRITE) -> SELECT ... FOR UPDATE 실행 -> 트랜잭션 종료 전까지 다른 UPDATE 차단
      ```
      [PessimisticLock] SELECT ... FOR UPDATE 실행, stockId=1
      [PessimisticLock] Tx2 대기중... (Tx1 commit 이후 진행)
      ```
  - 트랜잭션 격리 수준 실험 (@Transactional)
    - [기본 값] :  @Transactional(isolation = Isolation.READ_COMMITTED)
    - [동일 트랜잭션 내에서 값 일관성 보장] :  @Transactional(isolation = Isolation.REPEATABLE_READ)
    - [직렬 실행 처럼 동작] : @Transactional(isolation = Isolation.SERIALIZABLE)
      ```
      [IsolationTest] READ_COMMITTED → 값이 변경됨
      [IsolationTest] REPEATABLE_READ → 같은 트랜잭션에서 일관성 유지
      ```
- 비동기 처리(lab.async)
  - @Async + @EventListener 기반 이벤트 처리
    - eventPublisher.publishEvent(new UpdateTask(...))
      @Async @EventListener 메서드에서 비동기 처리
      ```
      [EventPublisher] 이벤트 발행: stockId=1, qty=2
      [EventListener-AsyncThread-1] UPDATE 실행 → 재고 8
      ```
  - BlockingQueue 기반 Worker Thread 처리 (옵션)
    - BlockingQueue<UpdateTask> 에 적재 후 , 별도 Worker Thread 가 큐에서 꺼내  DB 반영
      ```
      [Queue] Task 제출: stockId=1, qty=2
      [Worker-1] Task 처리 시작
      [Worker-1] UPDATE 완료, 남은재고=8
      ```
- DB(lab.db)
  - MYSQL (로컬 개발 환경)
  - [위치] : lab.db.config 

  - JPA +  QueryDSL 기반 Repository 
    - JPAQueryFactory로 동적 쿼리  + 비관적 락 적용 
    ```
    [Querydsl] 실행된 SQL = select * from stock where id=1 for update
    ```

- Test(lab.test)
  - 멀티 스레드 환경에서 동시성 문제 (Lost Update) 재현 
  - [위치] : lab.test.concurrent
  - [원리] : ExecutorService + CountDownLatch → 동시 요청 발생 시 Lost Update 확인
    ```
    [Test] Tx1 SELECT → 재고=10
    [Test] Tx2 SELECT → 재고=10
    [Test] Tx1 UPDATE → 재고=8
    [Test] Tx2 UPDATE → 재고=8 (Lost Update 발생!)
    ```
  - 락 적용시 일관성 유지 검증 
  - [위치] : lab.test.locked
  - [원리] : 같은 시나리오에서 비관적 락 적용 -> Tx2가 Tx1 commit 이후 실행이 된다. 
    ```
    [Test] Tx1 SELECT FOR UPDATE → 재고=10
    [Test] Tx2 대기중 (lock held by Tx1)
    [Test] Tx1 UPDATE 완료 → 재고=8
    [Test] Tx2 SELECT FOR UPDATE → 재고=8
    [Test] Tx2 UPDATE → 재고=6
    ```
