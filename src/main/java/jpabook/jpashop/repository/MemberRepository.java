package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    /**
     * 상속받은 JpaRepository에
     * Save
     * SaveAll
     * FindById(FindOne)
     * FindAll
     * 등 이미 많은 것들이 구현돼있다.
     * memberRepository.찍어보면 많은 것들이 있다.
     *
     * FindBy___의 경우에도 당장엔 구현되어있지 않지만 아래와 같이 선언해주기만하면
     * "select m from Member m where m.name = ?" 이라는 SQL문이 생성되어 작동한다.
     */
    List<Member> findByName(String name);
}
