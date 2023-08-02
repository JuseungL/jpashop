package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    
    @Test
    @Transactional
    /**만약 @Rollback(false)가 없고 @Transactional만 있으면 자동으로 Rollback돼서 DB에 저장되지 않는다.
     * 따라서 @Rollback(false)를 붙이면 그대로 커밋된다. 그러나 Test에서는 롤백 하지 않는게 맞음
     */

    @Rollback(false)
    public void testMember() throws Exception {
        //given
        //비영속 = 엔티티 객체를 생성했지만 아직 영속성 컨텍스트에 저장하지 않은 상태
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        //저장한거랑 조회한거랑 동일
        Assertions.assertThat(findMember).isEqualTo(member);
        //영속성 컨텍스트에서 같은 식별자를 가졌으면 같은 걸로 본다.(SELECT 조차 하지않고 1차 캐시에서  들고와서 비교)
        System.out.println("findMember == member: " + (findMember == member));
    }
}