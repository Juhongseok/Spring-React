package com.jhs.manager.controller;

import com.jhs.manager.service.response.UpdateMemberForm;
import com.jhs.manager.service.MemberService;
import com.jhs.manager.service.request.LoginMemberRequest;
import com.jhs.manager.service.request.SaveMemberRequest;
import com.jhs.manager.service.request.UpdateMemberRequest;
import com.jhs.manager.service.response.LoginMemberResponse;
import com.jhs.manager.service.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     * @param request
     * @return
     */
    @PostMapping("/member")
    public String signUp(@Validated @RequestBody SaveMemberRequest request) {
        return memberService.saveMember(request);
    }

    /**
     * 로그인
     * @param request
     * @return
     */
    @GetMapping("/member/login")
    public LoginMemberResponse login(@Validated @RequestBody LoginMemberRequest request){
        return memberService.login(request);
    }

    /**
     * 멤버리스트 조회
     * @return
     */
    @GetMapping("/members")
    public List<MemberInfoResponse> getMemberList(){
        return memberService.getMembers();
    }

    /**
     * 멤버 한명 정보 조회
     * @param memberId
     * @return
     */
    @GetMapping("/member/{memberId}")
    public MemberInfoResponse getMemberInfo(@PathVariable("memberId") String memberId){
        return memberService.getMemberInfo(memberId);
    }

    /**
     * 멤버 삭제
     * @param memberId
     * @return
     */
    @DeleteMapping("/member/{memberId}")
    public String deleteMember(@PathVariable("memberId") String memberId){
        memberService.deleteMember(memberId);
        return "ok";
    }

    /**
     * 멤버 업데이트 폼 조회시 정보
     * @param memberId
     * @return
     */
    @GetMapping("/member/{memberId}/update")
    public UpdateMemberForm updateMemberForm(@PathVariable("memberId") String memberId) {
        return memberService.getUpdateMemberInfo(memberId);
    }

    /**
     * 멤버 업데이트
     * @param memberId
     * @param request
     * @return
     */
    @PatchMapping("/member/{memberId}")
    public String updateMember(@PathVariable("memberId") String memberId, @Validated @RequestBody UpdateMemberRequest request){
        request.setId(memberId);
        memberService.updateMember(request);
        return "ok";
    }
}
