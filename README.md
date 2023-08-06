# 스프링 부트 & JPA활용
- 지연 로딩과 조회 성능 최적화(xToOne)
  - Entity 직접 노출
  - Entity를 DTO로 변환
  - Entity를 DTO로 변환 -> Fetch Join 최적화
  - JPA로 Entity가 아닌 DTO를 바로 조회

  💡권장 순서
  1. 우선 엔티티를 DTO로 변환하는 방법을 선택
  2. 필요하면 페치 조인으로 성능을 최적화 한다. → 대부분의 성능 이슈가 해결
  3. 그래도 안되면 DTO로 직접 조회하는 방법을 사용
  4. 최후의 방법은 JPA가 제공하는 네이티브 SQL이나 스프링 JDBC Template을 사용해서 SQL을 직접 사용

  
- 컬렉션 조회 최적화(xToMany)
    - Entity 직접 노출
    - Entity를 DTO로 변환
    - Entity를 DTO로 변환 -> Fetch Join 최적화
    - Entity를 DTO로 변환 -> 페이징과 한계 돌파
    - JPA로 Entity가 아닌 DTO를 바로 조회 
    - JPA로 Entity가 아닌 DTO를 바로 조회 - 컬렉션 조회 최적화
    - JPA로 Entity가 아닌 DTO를 바로 조회 - 플랫 데이터 최적화

    💡권장 순서
    1. 엔티티 조회 방식으로 우선 접근 
    2. 페치조인으로 쿼리 수를 최적화 
    3. 컬렉션 최적화 
    4. 페이징 필요 hibernate.default_batch_fetch_size , @BatchSize 로 최적화 
    5. 페이징 필요X 페치 조인 사용 
    6. 엔티티 조회 방식으로 해결이 안되면 DTO 조회 방식 사용 
    7. DTO 조회 방식으로 해결이 안되면 NativeSQL or 스프링 JdbcTemplate