package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //이게 디폴트가 됨
@RequiredArgsConstructor
public class MemberService {

    //@Autowired //Field Injection
    //private MemberRepository memberRepository;

    private final MemberRepository memberRepository;
//    @Autowired //생성자 주입  -> 도중에 Set해서 바꿀일도 없음, 생성자 하나일 때 @Autowired생략가능
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    } //이 전체를 @AllArgsCOnstructor로 생략가능 (클래스 단위), @RequiredArgsCOnstructor로 하기

//    @Autowired //Setter Injection
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
    /**
     * 회원가입
     */
    @Transactional //readOnly의 default는 false. 이건 읽기가 아니라 false로 해줘야해
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId(); //영속성 컨텍스트에서 저장될때 id가 key니까
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            //EXCEPTION
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 조회
     * 조회하는 메서드일 경우 @Transactional(readOnly = true)와 같이 readOnly = true
     * 성능 효과
     */

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
