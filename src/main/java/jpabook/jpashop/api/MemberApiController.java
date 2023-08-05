package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//@Controller+@ResponseBody
@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("api/v2/members")
    public Result memberV2(){
        List<Member> findMembers = memberService.findMembers();

        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    /**그냥 새로운 클래스를 만들지 않아도 된다는 장점
     * 엔티티 노출 -> 실무에선 절대 XXX
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member ){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * 이런 방식을 DTO로 받는다 라고 한다.
     * 이렇게 되면 Member 테이블의 필드들이 여기서 다 채워지는지 일부만 채워지는지
     * API스펙을 보지않는이상 알 방법이 없다. 그러나 이렇게 DTO(CreateMemberRequest)를 보게되면
     * 알 수 있다.
     * 또한 DTO에다가 Null값이 안될 때 @NotEmpty를 만들어서 넣어주기
     * 실무에서 엔티티를 절대 클라언트로 꺼내지말고
     * 엔티티와 APi 스펙을 구분할 수 있다는 장점
     */
    @PostMapping("/api/v2/members") //Member Entity가 수정됐을 때 .setXxx만 수정해주면됨. 엔티티를 숨길 수 있다.
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }


    //==DTO==//
    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    //DTO는 롬복 자유롭게 써도됨
    static class UpdateMemberResponse {
        private Long id;
        private String name;

    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> { //배열로 보내질때 json으로 감싸기
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }
}
