package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository//Data Access Layer
//Member 엔티티와 관련하여 데이터베이스 액세스를 수행하는 클래스
public class MemberRepository {
    @PersistenceContext //EntityManager를 주입(inject)
    //EntityManager는 JPA에서 엔티티와 데이터베이스 사이의 영속성 컨텍스트(엔티티를 영구 저장하는 환경)를 관리하는 클래스
    EntityManager em;
    public Long save(Member member) {
        //엔티티 매니저를 사용해 회원 엔티티를 영속성 컨텍스트에 저장(영속 -> em을 통해 영속성 컨텍스트에 저장한 상태)
        em.persist(member);
        //저장을 할 때 해당 멤버의 id를 반환하도록함
        return member.getId();
    }
    public Member find(Long id) {
        /**
         *  member를 저장해도 바로 INSERT SQL이 DB에 보내지는 것이 아니다
         *  엔티티 매니저는 트랜잭션을 커밋하기 직전까지 내부 쿼리 저장소에 INSERT SQL을 모아둔다.
         *  그리고 트랜잭션을 커밋할 때 모아둔 쿼리를 DB에 보낸다.
         *  이것을 트랜잭션을 지원하는 쓰기 지연
         *  이것을 DB에 반영할 때는 em.flush()
         */
        return em.find(Member.class, id);
    }
}