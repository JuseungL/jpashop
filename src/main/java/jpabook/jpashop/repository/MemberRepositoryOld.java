package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //스프링 빈 등록 - 내부에 @Component (컴포넌트 스캔의 대상)
@RequiredArgsConstructor
public class MemberRepositoryOld {

//    @Autowired
    private final EntityManager em;

    public void save(Member member ) {
        em.persist(member);
    }
    //.persist를 하면 해당 객체의 고유한 id와 함께 영속성 컨텍스트에 집어넣 -> key,value형식으로 넣어서

    public Member findOne(Long id) {
        return em.find(Member.class, id); //타입, pk
    }

    public List<Member> findAll() { // option command n으로 리턴, 로직 합치기
        return em.createQuery("SELECT m FROM Member m", Member.class) //JPQL - Table이 아 Entity인 member(m)를 조회해
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
